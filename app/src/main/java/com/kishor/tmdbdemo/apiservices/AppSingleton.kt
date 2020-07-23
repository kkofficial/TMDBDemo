package com.kishor.tmdbdemo.apiservices

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley

class AppSingleton private constructor(private val mContext: Context) {
    private var mRequestQueue: RequestQueue?
    val imageLoader: ImageLoader

    // getApplicationContext() is key, it keeps you from leaking the
    // Activity or BroadcastReceiver if someone passes one in.
    val requestQueue: RequestQueue?
        get() {
            if (mRequestQueue == null) {
                // getApplicationContext() is key, it keeps you from leaking the
                // Activity or BroadcastReceiver if someone passes one in.
                mRequestQueue = Volley.newRequestQueue(mContext.applicationContext)
            }
            return mRequestQueue
        }

    fun <T> addToRequestQueue(req: Request<T>, tag: String?) {
        req.tag = tag
        requestQueue!!.add(req)
    }

    fun cancelPendingRequests(tag: Any?) {
        if (mRequestQueue != null) {
            mRequestQueue!!.cancelAll(tag)
        }
    }

    companion object {
        private var mAppSingletonInstance: AppSingleton? = null

        @JvmStatic
        @Synchronized
        fun getInstance(context: Context): AppSingleton? {
            if (mAppSingletonInstance == null) {
                mAppSingletonInstance = AppSingleton(context)
            }
            return mAppSingletonInstance
        }
    }

    init {
        mRequestQueue = requestQueue
        imageLoader = ImageLoader(mRequestQueue,
            object : ImageLoader.ImageCache {
                private val cache = LruCache<String, Bitmap>(20)
                override fun getBitmap(url: String): Bitmap {
                    return cache[url]
                }

                override fun putBitmap(url: String, bitmap: Bitmap) {
                    cache.put(url, bitmap)
                }
            })
    }
}