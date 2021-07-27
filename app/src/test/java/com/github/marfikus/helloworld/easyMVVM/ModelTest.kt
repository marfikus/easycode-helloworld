package com.github.marfikus.helloworld.easyMVVM

import org.junit.Assert.*
import org.junit.Test

class ModelTest {

    @Test
    fun test_start_with_saved_value() {
        val testDataSource = TestDataSource()
        val model = Model(testDataSource)
        val callback = TestCallback()
        testDataSource.saveInt("", 5)
        model.start(callback)
        Thread.sleep(1)
        val actual = callback.text
        val expected = "6"
        assertEquals(expected, actual)
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