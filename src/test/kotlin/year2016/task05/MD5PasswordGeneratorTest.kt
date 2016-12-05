package year2016.task05

import org.junit.Assert.assertEquals
import org.junit.Test

class MD5PasswordGeneratorTest {
    @Test
    fun generate() {
        assertEquals(
                "18f47a30",
                generatePassword("abc", 8)
        )
    }
}