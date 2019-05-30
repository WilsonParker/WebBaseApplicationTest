package com.dev.hare.firebasepushmodule.service.abstracts

import android.app.Activity
import com.dev.hare.firebasepushmodule.service.abstracts.images.AbstractBackgroundImageDownloadService
import com.google.firebase.messaging.RemoteMessage

abstract class AbstractFirebaseMessagingBackgroundService<activity: Activity, service : AbstractBackgroundImageDownloadService<activity>> :
    AbstractFirebaseMessagingServive<service>() {

    override fun run(remoteMessage: RemoteMessage) {
        val intent = createIntent(remoteMessage)
        startService(intent)
    }
}