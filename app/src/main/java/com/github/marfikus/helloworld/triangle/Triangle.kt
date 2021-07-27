package com.github.marfikus.helloworld.triangle

class Triangle(private val sideA: Int,
               private val sideB: Int,
               private val sideC: Int) {

    init {
        if (sideA <= 0 || sideB <= 0 || sideC <= 0) {
            throw IllegalArgumentException("triangle sides cannot be non-positive")
        }
        if (sideA + sideB <= sideC || sideA + sideC <= sideB || sideB + sideC <= sideA) {
            throw IllegalArgumentException("invalid triangle with sides: $sideA $sideB $sideC")
        }
    }

    fun isRightTriangle(): Boolean {
        return sideA.square() + sideB.square() == sideC.square() ||
                sideA.square() + sideC.square() == sideB.square() ||
                sideB.square() + sideC.square() == sideA.square()
    }
}

private fun Int.square(): Int {
    return this * this
}