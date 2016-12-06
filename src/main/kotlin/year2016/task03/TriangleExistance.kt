package year2016.task03

import java.nio.file.Files
import java.nio.file.Paths

fun checkTriangleExistance(a: Int, b: Int, c: Int): Boolean {
    return when {
        a + b <= c -> false
        a + c <= b -> false
        b + c <= a -> false
        else -> true
    }
}

fun main(args: Array<String>) {
    println(
            Files.lines(Paths.get(ClassLoader.getSystemResource("year2016/task03/input.txt").toURI()))
                    .map(String::trim)
                    .map { str -> str.split(Regex("\\s+")) }
                    .map { strs -> strs.map(String::toInt).toIntArray() }
                    .filter { ints -> checkTriangleExistance(ints[0], ints[1], ints[2]) }
                    .count()
    )
}
