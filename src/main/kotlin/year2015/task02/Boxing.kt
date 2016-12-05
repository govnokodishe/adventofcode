package year2015.task02

import java.nio.file.Files
import java.nio.file.Paths

data class Box(val l: Int,
               val w: Int,
               val h: Int)

data class Tuple3<out T>(val first: T,
                         val second: T,
                         val third: T)

fun Box.sideSurfaces(): Tuple3<Int> {
    return Tuple3(l*w, w*h, h*l)
}

fun Tuple3<Int>.smallest(): Int {
    return arrayOf(first, second, third).min()!!
}

fun Box.wrappingSurface(): Int {
    val sides = sideSurfaces()
    return 2 * (sides.first + sides.second + sides.third) + sides.smallest()
}

fun String.parseBox(): Box {
    val strs = split("x")
    if (strs.size != 3) {
        throw IllegalArgumentException("Expecting 3 parts, got ${strs.size}")
    }
    val ints = strs.map(String::toInt).toIntArray()
    return Box(ints[0], ints[1], ints[2])
}

fun main(args: Array<String>) {
    println(
            Files.lines(Paths.get(ClassLoader.getSystemResource("year2015/task02/input.txt").toURI()))
                    .map(String::trim)
                    .map(String::parseBox)
                    .mapToInt(Box::wrappingSurface)
                    .sum()
    )
}
