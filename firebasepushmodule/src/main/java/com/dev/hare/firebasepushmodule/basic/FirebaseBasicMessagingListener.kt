package com.dev.hare.firebasepushmodule.basic

import com.dev.hare.firebasepushmodule.example.ExampleMainActivity
import com.dev.hare.firebasepushmodule.services.abstracts.AbstractFirebaseMessagingBackgroundService

class FirebaseBasicMessagingListener: AbstractFirebaseMessagingBackgroundService<ExampleMainActivity, FirebaseBasicWorkerImageDownloadService>() {
    override val serviceClass = FirebaseBasicWorkerImageDownloadService::class
}