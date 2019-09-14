package com.example.vtbhackathonproject.services

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FcmMessaging  : FirebaseMessagingService(){

//    override fun onCreate() {
//        super.onCreate()
//    }
//
//    override fun onMessageSent(s: String) {
//        super.onMessageSent(s)
//    }
//
//    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        if (SharedPreferencesWork.getInstance().hasUserToken()) {
//            val context = applicationContext
//            val creator = NotificationCreator(context)
//            creator.showNotification(context, remoteMessage)
//        }
//    }
//
//    override fun onNewToken(token: String) {
//        super.onNewToken(token)
//        sendRefreshedToken(token)
//    }
//
//    private fun sendRefreshedToken(refreshedToken: String) {
//        val requestBuilder = RequestBuilder(this)
//        requestBuilder.syncRegID(
//            refreshedToken,
//            if (BuildUtils.isDriverApp(BaseNabiApplication.getAppContext())) BuildUtils.DRIVER_APP else BuildUtils.PASSENGER_APP
//        )
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//    }
}