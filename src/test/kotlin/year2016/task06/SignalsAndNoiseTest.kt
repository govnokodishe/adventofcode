package year2016.task06

import org.junit.Assert.assertEquals
import org.junit.Test

class SignalsAndNoiseTest {
    @Test
    fun test() {
        assertEquals(
                "easter",
                sequenceOf(
                        "eedadn",
                        "drvtee",
                        "eandsr",
                        "raavrd",
                        "atevrs",
                        "tsrnev",
                        "sdttsa",
                        "rasrtv",
                        "nssdts",
                        "ntnada",
                        "svetve",
                        "tesnvt",
                        "vntsnd",
                        "vrdear",
                        "dvrsen",
                        "enarar"
                ).correctedString()
        )
    }
}
