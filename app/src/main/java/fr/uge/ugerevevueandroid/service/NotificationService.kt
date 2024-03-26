package fr.uge.ugerevevueandroid.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import android.provider.Settings
import fr.uge.ugerevevueandroid.MainActivity

class NotificationService : Service() {

    companion object {
        private const val NOTIFICATION_ID = 123
        private const val CHANNEL_ID = "my_channel"
        private const val CHANNEL_NAME = "My Channel"
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Vérifiez si l'intent n'est pas nul et s'il contient des extras pour le titre et le contenu
        intent?.let {
            val title = it.getStringExtra("title")
            val content = it.getStringExtra("content")
            // Vérifiez si le titre et le contenu ne sont pas nuls, puis affichez la notification
            if (!title.isNullOrEmpty() && !content.isNullOrEmpty()) {
                showNotification(title, content)
            }
        }
        return START_STICKY
    }

    private fun showNotification(title: String, content: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create a notification intent
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        // Build the notification
        val notificationBuilder = NotificationCompat.Builder(this)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        // For Android Oreo and above, set the channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
            notificationBuilder.setChannelId(CHANNEL_ID)
        }
        // Build and display the notification
        val notification = notificationBuilder.build()
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}