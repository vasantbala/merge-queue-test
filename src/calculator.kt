package com.example.calculator

/**
 * Simple calculator used as a conflict target for merge queue experiments.
 * Modify individual functions in separate branches to simulate concurrent changes.
 * Modify the same line in two branches to simulate merge conflicts (Scenario 4).
 */
object Calculator {
    //Add method
    fun add(a: Int, b: Int): Int {
        return a + b
    }

    fun subtract(a: Int, b: Int): Int {
        return a - b
    }

    fun multiply(a: Int, b: Int): Int {
        return a * b
    }

    fun divide(a: Int, b: Int): Double {
        require(b != 0) { "Cannot divide by zero" }
        return a.toDouble() / b.toDouble()
    }

    fun square(n: Int): Int {
        return n * n
    }

    fun isEven(n: Int): Boolean {
        return n % 2 == 0
    }

    fun factorial(n: Int): Long {
        require(n >= 0) { "Factorial is not defined for negative numbers" }
        return if (n == 0) 1L else n * factorial(n - 1)
    }
}
