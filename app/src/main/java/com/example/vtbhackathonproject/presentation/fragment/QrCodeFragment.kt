package com.example.vtbhackathonproject.presentation.fragment

import android.Manifest.permission.CAMERA
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.os.Bundle
import com.example.vtbhackathonproject.model.QrCodeModel
import com.example.vtbhackathonproject.presentation.base.BaseFragment
import com.example.vtbhackathonproject.repository.base.BaseRepository
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Matrix
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.text.style.BulletSpan
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.lifecycle.LifecycleOwner
import com.example.vtbhackathonproject.R
import com.example.vtbhackathonproject.Receipt
import com.example.vtbhackathonproject.repository.LoginActivityRepository
import com.google.firebase.FirebaseApp
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import kotlinx.android.synthetic.main.fragment_scan_qr_code.*
import java.util.concurrent.TimeUnit
import com.google.firebase.functions.FirebaseFunctions
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException


class QrCodeFragment(private val repository: LoginActivityRepository) : BaseFragment<QrCodeModel>(repository), LifecycleOwner {

    private lateinit var preview: Preview
    private val lastAnalyzedStamp = 0L
    private lateinit var detector: FirebaseVisionBarcodeDetector

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scan_qr_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDetector()
        init()
    }

    override fun initModel(): QrCodeModel = QrCodeModel()

    private fun init() {
        if (mayRequestCamera())
            initCamera()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val permissionGranted = checkGranted(grantResults)
        if (requestCode == REQUEST_CAMERA && permissionGranted)
            init()
    }

    private fun initDetector() {
        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
            .setBarcodeFormats(
                FirebaseVisionBarcode.FORMAT_QR_CODE
            ).build()
        detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options)
    }

    private fun initCamera() {
        cameraView.post {
            val previewConfig = PreviewConfig.Builder().apply {
                setTargetResolution(Size(720, 1280))
            }.build()

            val imageAnalysisConfig = ImageAnalysisConfig.Builder().apply {
                val analyzerThread = HandlerThread("OCR").apply { start() }
                setCallbackHandler(Handler(analyzerThread.looper))
                setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
                setTargetResolution(Size(720, 1280))
            }.build()

            val imageAnalysis = ImageAnalysis(imageAnalysisConfig)
            imageAnalysis.setAnalyzer { imageProxy, rotationDegrees ->
                if (imageProxy?.image == null || imageProxy.image == null) return@setAnalyzer

                val timestamp = System.currentTimeMillis()
                if (timestamp - lastAnalyzedStamp >= TimeUnit.SECONDS.toMillis(1)) {
                    val visionImage = FirebaseVisionImage.fromMediaImage(
                        imageProxy.image!!,
                        getOrientationFromRotation(rotationDegrees)
                    )

                    runBarcodeScanner(visionImage)
                }
            }
            preview = Preview(previewConfig)
            startCamera(preview, imageAnalysis)
        }
    }

    private fun startCamera(preview: Preview, imageAnalysis: ImageAnalysis) {
        preview.setOnPreviewOutputUpdateListener {

            val parent = cameraView.parent as ViewGroup
            parent.removeView(cameraView)
            parent.addView(cameraView, 0)

            cameraView.surfaceTexture = it.surfaceTexture

            updateTransform()
        }
        CameraX.bindToLifecycle(this, imageAnalysis, preview)
    }

    private fun runBarcodeScanner(image: FirebaseVisionImage) {
        detector.detectInImage(image).addOnSuccessListener { barcodeList ->
            barcodeList.forEach { _ ->
                barcodeList[0].rawValue?.let {
                    try {
                        repository.receipt = Gson().fromJson(it, Receipt::class.java)
                        navigator.moveTo(
                            DistributeBillFragment(activityRepository as LoginActivityRepository),
                            true,
                            R.id.container
                        )
                    } catch (e : JsonSyntaxException) {

                    }
                }
            }
        }
    }

    private fun updateTransform() {
        val matrix = Matrix()

        val centerX = cameraView.width / 2f
        val centerY = cameraView.height / 2f

        val rotationDegrees = when (cameraView.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

        cameraView.setTransform(matrix)
    }

    private fun getOrientationFromRotation(rotationDegrees: Int): Int {
        return when (rotationDegrees) {
            0 -> FirebaseVisionImageMetadata.ROTATION_0
            90 -> FirebaseVisionImageMetadata.ROTATION_90
            180 -> FirebaseVisionImageMetadata.ROTATION_180
            270 -> FirebaseVisionImageMetadata.ROTATION_270
            else -> FirebaseVisionImageMetadata.ROTATION_90
        }
    }

    private fun mayRequestCamera(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity?.checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) {
                return true
            }
            if (shouldShowRequestPermissionRationale(CAMERA)) {
                requestPermission()
            } else {
                requestPermission()
            }
        } else {
            return true
        }
        return false
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.e("TAG", "requestCameraPermission: 4")
            requestPermissions(arrayOf(CAMERA), REQUEST_CAMERA)
        }
    }

    private fun checkGranted(grantResults: IntArray): Boolean {
        for (grantResult in grantResults)
            if (grantResult == PackageManager.PERMISSION_DENIED) return false
        return true
    }

    companion object {
        const val REQUEST_CAMERA = 1
    }
}