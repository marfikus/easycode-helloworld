package com.github.marfikus.helloworld.easyMVVM

import java.util.*

class TimerTicker : TimeTicker {

    private var callback: TimeTicker.Callback? = null
    private var timer: Timer? = null
    private val timerTask
        get() = object : TimerTask() {
            override fun run() {
                callback?.tick()
            }
        }

    override fun start(callback: TimeTicker.Callback, period: Long) {
        this.callback = callback
        timer = Timer()
        timer?.scheduleAtFixedRate(timerTask, 0, period)
    }

    override fun stop() {
        callback = null
        timer?.cancel()
        timer = null
    }
}