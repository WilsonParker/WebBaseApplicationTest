package com.dev.hare.webbasetemplatemodule.activity

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

abstract class BaseIntroActivity : AppCompatActivity() {
    protected abstract val introImageID: Int
    protected abstract val imageUrl: String
    protected abstract val launchTimeOut: Long
    protected abstract val splashTimeOut: Long

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        onCreateInit(savedInstanceState)
        Handler().postDelayed({ init() }, launchTimeOut)
    }

    private fun init() {
        val options = RequestOptions()
                .centerCrop()
                .placeholder(introImageID)
                .error(introImageID)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.NONE)

        Glide.with(this)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(options)
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .thumbnail(0.5f)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                        finish()
                        overridePendingTransition(0, 0)
                        return false
                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        Handler().postDelayed({
                            finish()
                            overridePendingTransition(0, 0)
                        }, splashTimeOut)
                        return false
                    }
                })
                .into(getIntroImageView())
    }

    abstract fun onCreateInit(savedInstanceState: Bundle?)
    abstract fun getIntroImageView(): ImageView
}
