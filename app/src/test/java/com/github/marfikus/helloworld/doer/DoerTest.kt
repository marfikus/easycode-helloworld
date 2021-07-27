package com.github.marfikus.helloworld.doer

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DoerTest {

    private lateinit var logger: TestLogger
    private lateinit var doer: Doer

    @Before
    fun setUp() {
        logger = TestLogger()
        doer = Doer(logger)
    }

    @After
    fun clear() {
        logger.logCallsCount = 0
    }

    @Test
    fun test_one_time_case() {
        doer.doMain()
        val actual = logger.logCallsCount
        val expected = 1
        assertEquals(expected, actual)
    }

    @Test
    fun test_two_time_case() {
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