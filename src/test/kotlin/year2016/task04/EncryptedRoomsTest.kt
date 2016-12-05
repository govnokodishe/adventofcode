package year2016.task04

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class EncryptedRoomsTest {
    @Test
    fun checkIsReal0() {
        assertTrue(
                EncryptedRoom(
                        listOf("aaaaa", "bbb", "z", "y", "x"),
                        123,
                        "abxyz"
                ).isReal()
        )
    }

    @Test
    fun checkIsReal1() {
        assertTrue(
                EncryptedRoom(
                        listOf("a", "b", "c", "d", "e", "f", "g", "h"),
                        987,
                        "abcde"
                ).isReal()
        )
    }

    @Test
    fun checkIsReal2() {
        assertTrue(
                EncryptedRoom(
                        listOf("not", "a", "real", "room"),
                        404,
                        "oarel"
                ).isReal()
        )
    }

    @Test
    fun checkIsReal3() {
        assertFalse(
                EncryptedRoom(
                        listOf("totally", "real", "room"),
                        200,
                        "decoy"
                ).isReal()
        )
    }
}
