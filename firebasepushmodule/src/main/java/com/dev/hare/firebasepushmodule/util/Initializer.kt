package com.dev.hare.firebasepushmodule.util

import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import java.util.concurrent.Executors

object Initializer {
    fun initialize(context: Context) {
        WorkManager.initialize(
            context,
            Configuration.Builder()
                .setExecutor(Executors.newFixedThreadPool(8))
                .build()
        )
    }
}