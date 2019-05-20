package com.dev.hare.firebasepushmodule.basic

import com.dev.hare.firebasepushmodule.service.abstracts.AbstractFirebaseMessagingForegroundService
import com.dev.hare.firebasepushmodule.service.abstracts.AbstractImageDownloadService

class FirebaseBasicMessagingListener: AbstractFirebaseMessagingForegroundService() {
    override val serviceClass: Class<AbstractImageDownloadService>
        get() = FirebaseBasicImageDownloadService::class.java as Class<AbstractImageDownloadService>

}