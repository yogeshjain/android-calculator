package com.yogesh.calculator.math

import org.junit.Assert.*
import org.junit.Test
import java.security.InvalidParameterException

/**
 * Created by yogesh on 25/2/18.
 */
class EvaluatorTest {
    @Test
    fun eval() {
        assertEquals("", Evaluator.eval(""))
        assertEquals("3", Evaluator.eval("+3"))
        assertEquals("-9", Evaluator.eval("-9"))
        assertEquals("3", Evaluator.eval("1+2"))
        assertEquals("1", Evaluator.eval("-1+2"))
        assertEquals("-11", Evaluator.eval("-9-2"))
        assertEquals("2", Evaluator.eval("1+1-2-3+1+4"))
        assertEquals("3.5", Evaluator.eval("1.1+2.4"))
        assertEquals("8", Evaluator.eval("2^3"))
        assertEquals("1", Evaluator.eval("5%4"))
        assertEquals("12", Evaluator.eval("4*3"))
        assertEquals("7", Evaluator.eval("14/2"))
        assertEquals("25", Evaluator.eval("-6-5+4*3^2"))

    }

    @Test
    fun exceptions() {
        try {
            Evaluator.eval("3-")
            fail()
        } catch (th: Throwable) {
            assertTrue(th is InvalidParameterException)
        }

        try {
            Evaluator.eval("6/0")
            fail()
        } catch (th: Throwable) {
            assertTrue(th is InvalidParameterException)
        }
        try {
            Evaluator.eval("3^^")
            fail()
        } catch (th: Throwable) {
            assertTrue(th is NumberFormatException)
        }

        try {
            Evaluator.eval("*3")
            fail()
        } catch (th: Throwable) {
            assertTrue(th is InvalidParameterException)
        }

        try {
            Evaluator.eval("+33-")
            fail()
        } catch (th: Throwable) {
            assertTrue(th is InvalidParameterException)
        }

        try {
            Evaluator.eval("*")
            fail()
        } catch (th: Throwable) {
            assertTrue(th is InvalidParameterException)
        }
    }

}