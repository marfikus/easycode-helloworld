package com.github.marfikus.helloworld.doer

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