package com.github.marfikus.helloworld.easyMVVM

import android.util.Log
import java.util.*

class Model(private val dataSource: DataSource) {

    private var timer: Timer? = null
    private var timerTask: TimerTask? = null
    private var callback: TextCallback? = null
    private var count = -1

    fun start(textCallback: TextCallback) {
        callback = textCallback
        Log.d(TAG, "start: count is $count")
        if (count < 0)
            count = dataSource.getInt(COUNTER_KEY)
        Log.d(TAG, "started with count $count")

        timer = Timer()

        timerTask = object : TimerTask() {
            override fun run() {
                count++
                callback?.updateText(count.toString())
            }
        }

        timer?.scheduleAtFixedRate(timerTask, 0, 1000)
    }

    fun stop() {
        Log.d(TAG, "stop with count $count")
        dataSource.saveInt(COUNTER_KEY, count)
        timer?.cancel()
        timer = null
        timerTask = null
    }

    companion object {
        private const val COUNTER_KEY = "counterKey"
        private const val TAG = "uniqueCounterTag"
    }
}