package com.dev.hare.firebasepushmodule.worker

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.dev.hare.firebasepushmodule.service.abstracts.images.AbstractImageDownloadService.Companion.model
import com.dev.hare.firebasepushmodule.worker.WorkerConstants.Companion.KEY_IMAGE_URL
import com.dev.hare.hareutilitymodule.util.img.ImageUtil

class DownloadImageWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    @SuppressLint("RestrictedApi")
    override fun doWork(): Result {
        inputData.getString(KEY_IMAGE_URL)?.let {
            model?.pictureStyleModel?.bigPicture = ImageUtil().urlToBitmap(it)
        }
        return Result.success()
    }

}