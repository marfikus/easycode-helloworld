package com.github.marfikus.helloworld

import java.util.*

object Model {

    private lateinit var textCallback: TextCallback
    private var timer: Timer? = null

    private var count = 0

    fun init(callback: TextCallback) {
        textCallback = callback
    }

    fun start() {
        timer?.cancel()
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                count++
                textCallback.updateText(count.toString())
            }
        }, 1000, 1000)
    }
}