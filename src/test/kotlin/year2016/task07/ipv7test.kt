package year2016.task07

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ipv7test {
    @Test
    fun test1() {
        assertTrue(
                IpAddress(
                        IpAddressNode.Ordinal("abba",
                                IpAddressNode.Hypernet("mnop",
                                        IpAddressNode.Ordinal("qrst")
                                )
                        )
                ).supportsTls()
        )
    }

    @Test
    fun test2() {
        assertFalse(
                IpAddress(
                        IpAddressNode.Ordinal("abcd",
                                IpAddressNode.Hypernet("bddb",
                                        IpAddressNode.Ordinal("xyyx")
                                )
                        )
                ).supportsTls()
        )
    }

    @Test
    fun test3() {
        assertFalse(
                IpAddress(
                        IpAddressNode.Ordinal("aaaa",
                                IpAddressNode.Hypernet("qwer",
                                        IpAddressNode.Ordinal("tyui")
                                )
                        )
                ).supportsTls()
        )
    }

    @Test
    fun test4() {
        assertTrue(
                IpAddress(
                        IpAddressNode.Ordinal("ioxxoj",
                                IpAddressNode.Hypernet("asdfgh",
                                        IpAddressNode.Ordinal("zxcvbn")
                                )
                        )
                ).supportsTls()
        )
    }
}