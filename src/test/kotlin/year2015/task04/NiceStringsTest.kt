package year2015.task04

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import year2015.task05.isNice

class NiceStringsTest {
    @Test
    fun test0() {
        assertTrue("ugknbfddgicrmopn".isNice())
    }

    @Test
    fun test1() {
        assertTrue("aaa".isNice())
    }

    @Test
    fun test2() {
        assertFalse("jchzalrnumimnmhp".isNice())
    }

    @Test
    fun test3() {
        assertFalse("haegwjzuvuyypxyu".isNice())
    }

    @Test
    fun test4() {
        assertFalse("dvszwmarrgswjxmb".isNice())
    }
}
