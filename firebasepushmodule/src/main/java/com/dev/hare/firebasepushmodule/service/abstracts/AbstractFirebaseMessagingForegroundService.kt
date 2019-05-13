package com.dev.hare.firebasepushmodule.service.abstracts

import android.content.Intent
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

    override fun createIntent(remoteMessage: RemoteMessage): Intent {
        val intent = Intent()
        intent.setClass(baseContext, serviceClass)
        intent.putExtra(AbstractImageDownloadService.KEY_URL, remoteMessage.data[AbstractImageDownloadService.KEY_URL])
        intent.putExtra(AbstractImageDownloadService.KEY_REMOTE_MESSAGE, remoteMessage)
        return intent
    }
}