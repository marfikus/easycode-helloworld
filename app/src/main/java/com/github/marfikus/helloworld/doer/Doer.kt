package com.github.marfikus.helloworld.doer

import android.util.Log

class Doer {

    private var mainThingDone = false
    private val logger = LoggingTool()

    fun doMain() {
        if (!mainThingDone) {
            logger.log("main thing done")
            mainThingDone = true
        }
    }
}

class LoggingTool {

    fun log(message: String) {
        Log.d(javaClass.canonicalName, message)
    }
}