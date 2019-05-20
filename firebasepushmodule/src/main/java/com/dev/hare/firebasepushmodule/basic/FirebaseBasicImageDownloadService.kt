package com.dev.hare.firebasepushmodule.basic

import android.app.Activity
import com.dev.hare.firebasepushmodule.example.ExampleMainActivity
import com.dev.hare.firebasepushmodule.service.abstracts.AbstractImageDownloadService

class FirebaseBasicImageDownloadService : AbstractImageDownloadService() {
    override val activityCalss: Class<Activity>
        get() = ExampleMainActivity::class.java as Class<Activity>
    override val _channelID = "FirebasePushModule_ID"
    override val _channelName = "FirebasePushModule_Name"

}