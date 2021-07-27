package com.github.marfikus.helloworld.doer

import org.junit.Assert.*
import org.junit.Test

class DoerTest {

    @Test
    fun test_one_time_case() {
        val logger = TestLogger()
        val doer = Doer(logger)

        doer.doMain()
        val actual = logger.logCallsCount
        val expected = 1
        assertEquals(expected, actual)
    }

    @Test
    fun test_two_time_case() {
        val logger = TestLogger()
        val doer = Doer(logger)

        doer.doMain()
        doer.doMain()
        val actual = logger.logCallsCount
        val expected = 1
        assertEquals(expected, actual)
    }

    private inner class TestLogger : Logging {

        var logCallsCount = 0

        override fun log(message: String) {
            logCallsCount++
        }

    }
}