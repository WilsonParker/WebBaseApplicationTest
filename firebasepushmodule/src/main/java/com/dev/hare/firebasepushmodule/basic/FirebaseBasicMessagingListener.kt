package com.dev.hare.firebasepushmodule.basic

import com.dev.hare.firebasepushmodule.service.abstracts.AbstractFirebaseMessagingForegroundService

class FirebaseBasicMessagingListener: AbstractFirebaseMessagingForegroundService() {
    override val serviceClass: Class<*>
        get() = FirebaesBasicImageDownloadService::class.java

}