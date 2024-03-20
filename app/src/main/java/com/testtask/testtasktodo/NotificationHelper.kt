package com.testtask.testtasktodo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavDeepLinkBuilder
import com.testtask.testtasktodo.NotificationHelper.Companion.NOTIFICATION_ID

class NotificationHelper(private val context: Context) {

    fun setNotification(textFromDesc: String, delayMillis: Long) {
        val mainFragmentPendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_second)
            .setDestination(R.id.mainFragment2)
            .createPendingIntent()

        val completeIntent = Intent(context, NotificationActionReceiver::class.java)
        completeIntent.action = "ACTION_COMPLETE_TODO"
        val completePendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            completeIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Напоминание")
            .setContentText(textFromDesc)
            .setSmallIcon(R.drawable.baseline_add_alert_24)
            .addAction(R.drawable.baseline_add_alert_24, "Выполнить", completePendingIntent)
            .setContentIntent(mainFragmentPendingIntent)
            .setAutoCancel(true)

        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Уведомление",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
        }, delayMillis)
    }

    companion object {
        const val CHANNEL_ID = "AlertChannel"
        const val NOTIFICATION_ID = 123
    }
}


class NotificationActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action == "ACTION_COMPLETE_TODO") {
            val todoId = intent.getLongExtra("todo_id", -1)
            if (todoId != -1L) {
                val notificationManager = NotificationManagerCompat.from(context)
                notificationManager.cancel(NOTIFICATION_ID)
            }
        }
    }
}


