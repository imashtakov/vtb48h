package com.example.vtbhackathonproject.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import com.example.vtbhackathonproject.R
import com.example.vtbhackathonproject.presentation.FragmentNavigator
import com.example.vtbhackathonproject.presentation.base.BaseActivity
import com.example.vtbhackathonproject.repository.MainActivityRepository

class MainActivity : BaseActivity<MainActivityRepository>() {

    companion object {
        val TAG = MainActivity::class.simpleName
    }

    lateinit var navigator: FragmentNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigator = FragmentNavigator(supportFragmentManager)
//        val intent = Intent("com.google.zxing.client.android.SCAN")
//        intent.setPackage("com.google.zxing.client.android")
//        intent.putExtra("SCAN_MODE", "QR_CODE_MODE")
//        startActivityForResult(intent, 0)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 0) {
//            if (resultCode == Activity.RESULT_OK) {
//                val contents = data?.getStringExtra("SCAN_RESULT")
//                val format = data?.getStringExtra("SCAN_RESULT_FORMAT")
//                print("Result : $contents, $format")
//                // Handle successful scan
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                // Handle cancel
//            }
//        }
//    }

    override fun initRepository(): MainActivityRepository =
        MainActivityRepository(PreferenceManager.getDefaultSharedPreferences(this))

    private fun startLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
