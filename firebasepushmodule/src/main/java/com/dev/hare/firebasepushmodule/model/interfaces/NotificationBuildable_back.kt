package com.dev.hare.firebasepushmodule.model.interfaces

import android.app.*
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import kotlin.reflect.KClass

interface NotificationBuildable_back {
    fun createDefaultOwnNotification(): Notification

    fun createOwnNotification(): Notification

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun createDefaultNotificationChannel(notificationManager: NotificationManager): NotificationChannel

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun createNotificationChannel(notificationManager: NotificationManager): NotificationChannel

    fun createDefaultNotificationCompatBuilder(): NotificationCompat.Builder

    fun createNotificationCompatBuilder(): NotificationCompat.Builder

    @RequiresApi(Build.VERSION_CODES.O)
    fun createDefaultNotificationBuilder(): Notification.Builder

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationBuilder(): Notification.Builder

    fun createDefaultPendingIntent(activity: Class<out Activity>): PendingIntent

    fun createPendingIntent(activity: Class<out Activity>): PendingIntent

}