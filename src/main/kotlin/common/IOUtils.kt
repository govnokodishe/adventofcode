package common

import java.io.InputStreamReader
import java.io.Reader

object IOUtils {
    fun <T> useLinesFromResource(path: String, linesFunction: (Sequence<String>) -> T): T {
        return javaClass.getResourceAsStream(path).use { inputStream ->
            InputStreamReader(inputStream).use { reader: Reader ->
                reader.useLines(linesFunction)
            }
        }
    }
}
