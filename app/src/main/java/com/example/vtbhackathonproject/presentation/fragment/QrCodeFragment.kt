package com.example.vtbhackathonproject.presentation.fragment

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.os.Bundle
import com.example.vtbhackathonproject.model.QrCodeModel
import com.example.vtbhackathonproject.presentation.base.BaseFragment
import com.example.vtbhackathonproject.repository.base.BaseRepository
import android.content.Intent



class QrCodeFragment(repository: BaseRepository) : BaseFragment<QrCodeModel, BaseRepository>(repository) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent("com.google.zxing.client.android.SCAN")
        intent.setPackage("com.google.zxing.client.android")
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE")
        startActivityForResult(intent, 0)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                val contents = data?.getStringExtra("SCAN_RESULT")
                val format = data?.getStringExtra("SCAN_RESULT_FORMAT")
                print("Result : $contents, $format")
                // Handle successful scan
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }
    }

    override fun initModel(): QrCodeModel = QrCodeModel()
}