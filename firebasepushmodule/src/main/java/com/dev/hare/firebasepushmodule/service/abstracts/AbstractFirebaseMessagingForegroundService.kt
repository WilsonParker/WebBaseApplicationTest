package com.dev.hare.firebasepushmodule.service.abstracts

import android.os.Build
import com.google.firebase.messaging.RemoteMessage

abstract class AbstractFirebaseMessagingForegroundService : AbstractFirebaseMessagingServive() {

    override fun run(remoteMessage: RemoteMessage) {
        val intent = createIntent(remoteMessage)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startForegroundService(intent)
        else
            startService(intent)
    }

}