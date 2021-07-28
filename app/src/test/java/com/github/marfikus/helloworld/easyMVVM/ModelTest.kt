package com.github.marfikus.helloworld.easyMVVM

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ModelTest {

    private lateinit var testDataSource: TestDataSource
    private lateinit var testTimeTicker: TestTimeTicker
    private lateinit var model: Model
    private lateinit var testCallback: TestCallback

    @Before
    fun setUp() {
        testDataSource = TestDataSource()
        testTimeTicker = TestTimeTicker()
        model = Model(testDataSource, testTimeTicker)
        testCallback = TestCallback()
    }

    @After
    fun clear() {

    }

    @Test
    fun test_start_with_saved_value() {
        testDataSource.saveInt("", 5)
        model.start(testCallback)
        testTimeTicker.tick(1)
        val actual = testCallback.text
        val expected = "6"
        assertEquals(expected, actual)
    }

    @Test
    fun test_stop_after_2_seconds() {
        testDataSource.saveInt("", 0)
        model.start(testCallback)
        testTimeTicker.tick(2)
        val actual = testCallback.text
        val expected = "2"
        assertEquals(expected, actual)

        model.stop()
        val savedCountActual = testDataSource.getInt("")
        val savedCountExpected = 2
        assertEquals(savedCountExpected, savedCountActual)
    }

    @Test
    fun test_start_after_stop() {
        testDataSource.saveInt("", 10)
        model.start(testCallback)
        testTimeTicker.tick(2)
        val actual = testCallback.text
        val expected = "12"
        assertEquals(expected, actual)

        model.stop()
        val savedCountActual = testDataSource.getInt("")
        val savedCountExpected = 12
        assertEquals(savedCountExpected, savedCountActual)

        model.start(testCallback)
        testTimeTicker.tick(3)
        val actualText = testCallback.text
        val expectedText = "15"
        assertEquals(expectedText, actualText)
    }

    @Test
    fun test_start_after_dead_app() {
        // первый запуск приложения
        var testDataSourceNullable: TestDataSource? = TestDataSource()
        var testTimeTickerNullable: TestTimeTicker? = TestTimeTicker()
        var modelNullable: Model? = Model(testDataSourceNullable!!, testTimeTickerNullable!!)
        var testCallbackNullable: TestCallback? = TestCallback()
        testDataSourceNullable.saveInt("", 5)
        modelNullable!!.start(testCallbackNullable!!)
        testTimeTickerNullable.tick(3)
        val actual = testCallbackNullable.text
        val expected = "8"
        assertEquals(expected, actual)

        // остановка (проверка сохранения)
        modelNullable.stop()
        val savedCountActual = testDataSourceNullable.getInt("")
        val savedCountExpected = 8
        assertEquals(savedCountExpected, savedCountActual)

        // имитация завершения приложения
        testDataSourceNullable = null
        testTimeTickerNullable = null
        modelNullable = null
        testCallbackNullable = null

        // новый запуск приложения
        val newTestDataSource = TestDataSource()
        val newTestTimeTicker = TestTimeTicker()
        val newModel = Model(newTestDataSource, newTestTimeTicker)
        val newCallback = TestCallback()
        newTestDataSource.saveInt("", savedCountActual)
        newModel.start(newCallback)
        newTestTimeTicker.tick(10)
        val newActual = newCallback.text
        val newExpected = "18"
        assertEquals(newExpected, newActual)

        // ещё раз остановка
        newModel.stop()
        val newSavedCountActual = newTestDataSource.getInt("")
        val newSavedCountExpected = 18
        assertEquals(newSavedCountExpected, newSavedCountActual)
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