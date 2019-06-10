package com.app.f49.firebase

import android.content.Intent
import android.util.Log
import com.app.f49.MainActivity
import com.app.f49.model.notification.NotificationVO
import com.app.f49.utils.NotificationUtil
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {
    companion object {
        val TAG = "MyFirebaseMsgingService"
        val TITLE = "title"
        val ITEMID = "itemId"
        val SCREENID = "screenId"
        val ACTION_DESTINATION = "action_destination"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        if ((remoteMessage?.getData()?.size ?: 0) > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage?.getData())
            val data = remoteMessage?.getData()
            handleData(data?.toMutableMap()!!)

        } else if (remoteMessage?.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification()!!.getBody())
            handleNotification(remoteMessage.getNotification()!!)
        }// Check if message contains a notification payload.

    }

    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)
        Log.d("NewToken", p0 ?: "")
    }

    private fun handleNotification(RemoteMsgNotification: RemoteMessage.Notification) {
        val message = RemoteMsgNotification.body
        val title = RemoteMsgNotification.title
        val notificationVO = NotificationVO()
        notificationVO.title = title
        notificationVO.message = message

        val resultIntent = Intent(applicationContext, MainActivity::class.java)
        val notificationUtils = NotificationUtil(applicationContext)
        notificationUtils.displayNotification(notificationVO, resultIntent)
        notificationUtils.playNotificationSound()
    }

    private fun handleData(data: MutableMap<String, String>) {
        val title = data[TITLE]
        val itemId = data[ITEMID]
        val screenID = data[SCREENID]
        val actionDestination = data[ACTION_DESTINATION]
        val notificationVO = NotificationVO()
        notificationVO.title = title
        notificationVO.screenId = screenID
        notificationVO.itemId = itemId

        val resultIntent = Intent(applicationContext, MainActivity::class.java)

        val notificationUtils = NotificationUtil(applicationContext)
        notificationUtils.displayNotification(notificationVO, resultIntent)
        notificationUtils.playNotificationSound()

    }
}