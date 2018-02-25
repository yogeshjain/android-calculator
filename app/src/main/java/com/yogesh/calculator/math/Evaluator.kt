package com.yogesh.calculator.math

import java.math.RoundingMode
import java.security.InvalidParameterException
import java.text.DecimalFormat

/**
 * Created by yogesh on 24/2/18.
 */
internal object Evaluator {
    fun eval(str: String): String {
        var res = reduce(str)
        var prev = str
        while (res != prev) {
            prev = res
            res = reduce(res)
        }

        return res
    }

    private fun reduce(str: String): String {
        if (str.contains('^')) {
            return evalAround(str, str.indexOf('^'))
        } else if (str.contains(Regex("""[*/%]"""))) {
            val min = str.indexOfFirst { it.equals('*') || it.equals('/') || it.equals('%') }
            return evalAround(str, min)
        } else if (str.contains(Regex("""[+-]"""))) {
            var min = str.indexOfFirst { it.equals('+') || it.equals('-') }
            if (min == 0) {
                min = str.drop(1).indexOfFirst { it.equals('+') || it.equals('-') } + 1
            }
            if (min != 0) {
                return evalAround(str, min)
            } else if (str[0] == '+') {
                return str.drop(1)
            }
        }

        return str
    }

    private fun evalAround(str: String, i: Int): String {
        var lhsS = i - 1
        val lhsE = i
        val rhsS = i + 1
        var rhsE = i + 1
        if (i == str.length - 1 || i == 0) {
            throw InvalidParameterException("Invalid expression " + str)
        }
        while (lhsS >= 0 && (str[lhsS].isDigit() || str[lhsS] == '.' || str[lhsS] == '-')) {
            lhsS--
        }
        while (rhsE < str.length && (str[rhsE].isDigit() || str[rhsE] == '.')) {
            rhsE++
        }
        lhsS++
        val lhs = str.substring(lhsS, lhsE).toDouble()
        val rhs = str.substring(rhsS, rhsE).toDouble()

        val df = DecimalFormat("#.#####")
        df.roundingMode = RoundingMode.CEILING
        val result = df.format(valueOf(lhs, str[i], rhs))

        return str.substring(0, lhsS) + result + str.substring(rhsE, str.length)
    }

    private fun valueOf(lhs: Double, op: Char, rhs: Double): Double {
        when (op) {
            '^' -> return Math.pow(lhs, rhs)
            '/' -> if (rhs == 0.0) throw InvalidParameterException("Division by zero") else return lhs / rhs
            '*' -> return lhs * rhs
            '%' -> return lhs % rhs
            '+' -> return lhs + rhs
            '-' -> return lhs - rhs
            else -> return 0.0
        }
    }
}