package com.app.f49.firebase

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)

    }
//    private fun sendNotification(messageBody: String) {
//        val intent = Intent(this, MainActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//            PendingIntent.FLAG_ONE_SHOT)
//
//        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//        val notificationBuilder = NotificationCompat.Builder(this)
//            .setSmallIcon(R.drawable.ic_phone)
//            .setContentTitle("FCM Message")
//            .setContentText(messageBody)
//            .setAutoCancel(true)
//            .setSound(defaultSoundUri)
//            .setContentIntent(pendingIntent)
//
//        val notificationManager = getSystemService<Any>(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
//    }
}