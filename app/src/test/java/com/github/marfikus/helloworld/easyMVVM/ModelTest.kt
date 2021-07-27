package com.github.marfikus.helloworld.easyMVVM

import org.junit.Assert.*
import org.junit.Test

class ModelTest {

    @Test
    fun test_start_with_saved_value() {
        val testDataSource = TestDataSource()
        val testTimeTicker = TestTimeTicker()
        val model = Model(testDataSource, testTimeTicker)
        val callback = TestCallback()
        testDataSource.saveInt("", 5)
        model.start(callback)
        testTimeTicker.tick(1)
        val actual = callback.text
        val expected = "6"
        assertEquals(expected, actual)
    }

    @Test
    fun test_stop_after_2_seconds() {
        val testDataSource = TestDataSource()
        val testTimeTicker = TestTimeTicker()
        val model = Model(testDataSource, testTimeTicker)
        val callback = TestCallback()
        testDataSource.saveInt("", 0)
        model.start(callback)
        testTimeTicker.tick(2)
        val actual = callback.text
        val expected = "2"
        assertEquals(expected, actual)

        model.stop()
        val savedCountActual = testDataSource.getInt("")
        val savedCountExpected = 2
        assertEquals(savedCountExpected, savedCountActual)
    }
}

private class TestTimeTicker : TimeTicker {

    private var callback: TimeTicker.Callback? = null

    var state = 0

    override fun start(callback: TimeTicker.Callback, period: Long) {
        this.callback = callback
        state = 1
    }

    override fun stop() {
        callback = null
        state = -1
    }

    fun tick(times: Int) {
        for (i in 0 until times) {
            callback?.tick()
        }
    }
}

private class TestDataSource : DataSource {

    private var int: Int = Int.MAX_VALUE

    override fun saveInt(key: String, value: Int) {
        int = value
    }

    override fun getInt(key: String): Int = int
}

private class TestCallback : TextCallback {

    var text = ""

    override fun updateText(str: String) {
        text = str
    }
}