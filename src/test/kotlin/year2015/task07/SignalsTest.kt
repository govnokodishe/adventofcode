package year2015.task07

import org.junit.Assert.assertEquals
import org.junit.Test
import year2015.task07.SignalProvider.And
import year2015.task07.SignalProvider.Constant
import year2015.task07.SignalProvider.LeftShift
import year2015.task07.SignalProvider.Not
import year2015.task07.SignalProvider.Or
import year2015.task07.SignalProvider.RightShift
import year2015.task07.SignalProvider.WireProvider

class SignalsTest {
    @Test
    fun testLeftShift() {
        assertEquals(
                Signal(0xffff) shl 1 ,
                Signal(0xfffe)
        )
        assertEquals(
                Signal(0xffff) shl 4 ,
                Signal(0xfff0)
        )
    }

    @Test
    fun testRightShift() {
        assertEquals(
                Signal(0x0001) shr 1 ,
                Signal(0x0000)
        )
        assertEquals(
                Signal(0x0002) shr 1 ,
                Signal(0x0001)
        )
        assertEquals(
                Signal(0x000f) shr 4 ,
                Signal(0x0000)
        )
    }

    @Test
    fun test() {
        val expected = mapOf(
                Wire("x") to Signal(123),
                Wire("y") to Signal(456),
                Wire("d") to Signal(72),
                Wire("e") to Signal(507),
                Wire("f") to Signal(492),
                Wire("g") to Signal(114),
                Wire("h") to Signal(65412),
                Wire("i") to Signal(65079)
        )
        val actual = computeAll(
                mapOf(
                        Wire("x") to Constant(Signal(123)),
                        Wire("y") to Constant(Signal(456)),
                        Wire("d") to And(WireProvider(Wire("x")), WireProvider(Wire("y"))),
                        Wire("e") to Or(WireProvider(Wire("x")), WireProvider(Wire("y"))),
                        Wire("f") to LeftShift(WireProvider(Wire("x")), 2),
                        Wire("g") to RightShift(WireProvider(Wire("y")), 2),
                        Wire("h") to Not(WireProvider(Wire("x"))),
                        Wire("i") to Not(WireProvider(Wire("y")))
                )
        )
        assertEquals(
                expected,
                actual
        )
        println(actual)
    }
}