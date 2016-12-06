package year2015.task05

import java.nio.file.Files
import java.nio.file.Paths
import java.util.regex.Pattern

private val vowelPattern = Pattern.compile("[aeiou]")
private val doubleAlphaPattern = Pattern.compile(('a'..'z').map { "([$it]{2})" }.joinToString("|"))
private val naughtyStrings = Pattern.compile("(ab)|(cd)|(pq)|(xy)")

fun String.isNice(): Boolean {
    return containsAtLeast(vowelPattern, 3) &&
            containsAtLeast(doubleAlphaPattern, 1) &&
            indexOf(naughtyStrings)?.let { false } ?: true
}

fun String.containsAtLeast(pattern: Pattern, n: Int): Boolean {
    fun String.containsAtLeast(pattern: Pattern, n: Int, occursCount: Int): Boolean {
        return when (occursCount) {
            n -> true
            else -> {
                val index = indexOf(pattern)
                when (index) {
                    is Int -> substring(index + 1).containsAtLeast(pattern, n, occursCount + 1)
                    else -> return occursCount == n

                }
            }
        }
    }
    return containsAtLeast(pattern, n, 0)
}


fun String.indexOf(pattern: Pattern): Int? {
    val m = pattern.matcher(this)
    return when {
        m.find() -> m.start()
        else -> null
    }
}

fun main(args: Array<String>) {
    println(
            Files.lines(Paths.get(ClassLoader.getSystemResource("year2015/task05/input.txt").toURI()))
                    .map(String::trim)
                    .filter(String::isNice)
                    .count()
    )
}