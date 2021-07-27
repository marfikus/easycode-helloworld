package com.github.marfikus.helloworld.easyMVVM

interface TimeTicker {

    fun start(callback: Callback, period: Long = 1000)

    fun stop()

    interface Callback {

        fun tick()
    }
}