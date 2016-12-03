package year2015.task01

import year2015.task01.VerticalDirection.DOWN
import year2015.task01.VerticalDirection.UP
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

enum class VerticalDirection {
    UP,
    DOWN,
}

data class VerticalPosition(val height: Int)

fun VerticalPosition.move(vd: VerticalDirection): VerticalPosition {
    return when (vd) {
        VerticalDirection.UP -> VerticalPosition(height + 1)
        VerticalDirection.DOWN -> VerticalPosition(height - 1)
    }
}

fun Char.parseVerticalDirection(): VerticalDirection {
    return when (this) {
        '(' -> UP
        ')' -> DOWN
        else -> throw IllegalArgumentException("Unexpected char: $this")
    }
}

fun main(args: Array<String>) {
    println(
            String(
                    Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("year2015/task01/input.txt").toURI())),
                    StandardCharsets.UTF_8
            )
                    .map(Char::parseVerticalDirection)
                    .fold(VerticalPosition(0), VerticalPosition::move)
    )
}
