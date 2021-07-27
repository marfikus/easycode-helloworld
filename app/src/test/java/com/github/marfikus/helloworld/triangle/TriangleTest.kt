package com.github.marfikus.helloworld.triangle

import org.junit.Assert.*
import org.junit.Test

class TriangleTest {

    @Test
    fun test_valid_data() {
        val triangle = Triangle(3, 4, 5)
        val actual = triangle.isRightTriangle()
        val expected = true
        assertEquals(expected, actual)
    }

    @Test
    fun test_negative_data() {
        val triangle = Triangle(-3, -4, -5)
        val actual = triangle.isRightTriangle()
        val expected = false
        assertEquals(expected, actual)
    }

}