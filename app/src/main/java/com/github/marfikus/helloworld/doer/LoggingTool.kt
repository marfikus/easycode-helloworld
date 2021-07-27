package com.github.marfikus.helloworld.doer

import android.util.Log

class LoggingTool : Logging {

    override fun log(message: String) {
        Log.d(javaClass.canonicalName, message)
    }
}

interface Logging {

    fun log(message: String)
}