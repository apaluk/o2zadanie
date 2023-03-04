package com.example.o2zadanie.core.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.o2zadanie.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class O2NotificationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        const val ACTIVATION_CHANNEL_ID = "ACTIVATION_CHANNEL_ID"
    }
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val activationNotificationChannel: NotificationChannel? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            NotificationChannel(ACTIVATION_CHANNEL_ID, "Activation", NotificationManager.IMPORTANCE_DEFAULT)
        else null

    fun shouldRequestPermission(): Boolean =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                && context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED

    fun showActivationErrorNotification(code: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && activationNotificationChannel != null) {
            notificationManager.createNotificationChannel(activationNotificationChannel)
        }
        val notification = NotificationCompat.Builder(context, ACTIVATION_CHANNEL_ID)
            .setContentTitle("Activation error")
            .setContentText("Activation failed with code: $code")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        notificationManager.notify(322, notification)
    }
}