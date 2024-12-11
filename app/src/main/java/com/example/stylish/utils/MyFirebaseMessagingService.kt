package com.example.stylish.utils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.stylish.R
import com.example.stylish.ui.homepage.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService : FirebaseMessagingService() {

    val channelId = "notification_channel"
    val channelName = "com.example.stylish"


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("TAG", "onNewToken: ")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        remoteMessage.notification?.let {
            generateNotification(it.title!!, it.body!!)
        }

        if (remoteMessage.data.isNotEmpty()) {
            val title = remoteMessage.data["title"] ?: "Default Title"
            val message = remoteMessage.data["message"] ?: "Default Message"
            generateNotification(title, message)
        }
    }

    private fun generateNotification(title: String, message: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_IMMUTABLE)
        var notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.logo).setAutoCancel(true)
            .setVibrate(longArrayOf(100, 100, 100)).setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)



        notification = notification.setContent(getRemoteView(title, message))


        val notificationManger = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManger.createNotificationChannel(notificationChannel)
        }

        notificationManger.notify(0, notification.build())

    }

    @SuppressLint("RemoteViewLayout")
    private fun getRemoteView(title: String, message: String): RemoteViews? {
        val remoteView = RemoteViews("com.example.stylish", R.layout.offers_notification)
        remoteView.setTextViewText(R.id.not_title, title)
        remoteView.setTextViewText(R.id.not_description, message)
        remoteView.setImageViewResource(R.id.notifImg, R.drawable.logo)
        return remoteView
    }

}