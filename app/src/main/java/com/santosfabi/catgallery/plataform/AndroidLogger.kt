package com.santosfabi.catgallery.plataform

import com.santosfabi.catgallery.util.Logger

class AndroidLogger : Logger {
    override fun e(tag: String, message: String) {
        android.util.Log.e(tag, message)
    }

    override fun d(tag: String, message: String) {
        android.util.Log.d(tag, message)
    }
}
