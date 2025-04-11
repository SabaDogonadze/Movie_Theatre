package com.example.movietheatre.feature_notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.movietheatre.MainActivity
import com.example.movietheatre.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


const val CHANNEL_ID = "notifications_channel"
const val CHANNEL_NAME = "Notifications"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        message.notification?.let {
            // If there's an image URL in the notification payload
            val imageUrl = message.data["image_url"]
            generateNotification(it.title ?: "", it.body ?: "", imageUrl)
        }
    }

    fun generateNotification(title: String, message: String, imageUrl: String? = null) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        // Create notification channel for Android O and above
        createNotificationChannel()

        // Build a better looking notification
        val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_splash_logo)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(false)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(this, R.color.light_blue)) // Add your brand color
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)

        if (message.length > 40) {
            notificationBuilder.setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(message)
                    .setBigContentTitle(title)
            )
        }

        imageUrl?.let {
            try {

                val bitmap = loadImageBitmap(imageUrl)
                bitmap?.let {
                    notificationBuilder.setStyle(
                        NotificationCompat.BigPictureStyle()
                            .bigPicture(bitmap)
                            .setBigContentTitle(title)
                            .setSummaryText(message)
                    )
                    notificationBuilder.setLargeIcon(bitmap)
                }
            } catch (e: Exception) {
                Log.e("FCM", "Failed to load notification image", e)
            }
        }

        val viewAction = Intent(this, MainActivity::class.java).apply {
            putExtra("NOTIFICATION_ACTION", "VIEW")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val viewPendingIntent = PendingIntent.getActivity(
            this, 1, viewAction, PendingIntent.FLAG_IMMUTABLE
        )
        notificationBuilder.addAction(
            android.R.drawable.ic_menu_view, "View", viewPendingIntent
        )

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = System.currentTimeMillis().toInt()
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Movie Theatre Notifications"
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            vibrationPattern = longArrayOf(1000, 1000, 1000, 1000)
            setShowBadge(true)
        }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun loadImageBitmap(imageUrl: String): Bitmap? {
        return try {
            Glide.with(applicationContext)
                .asBitmap()
                .load(imageUrl)
                .submit(24, 24)
                .get()
        } catch (e: Exception) {
            Log.e("FCM", "Error loading image", e)
            null
        }
    }
}