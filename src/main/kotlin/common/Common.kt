package common

import common.Direction.DOWN
import common.Direction.LEFT
import common.Direction.RIGHT
import common.Direction.UP
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

data class Position(val x: Int, val y: Int)

enum class Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT,
}

fun Position.move(d: Direction): Position {
    return when (d) {
        UP -> Position(x, y + 1)
        RIGHT -> Position(x + 1, y)
        DOWN -> Position(x, y - 1)
        LEFT -> Position(x - 1, y)
    }
}

fun Map<Char, Int>.count(char: Char): Map<Char, Int> {
    return plus(char to (1 + (get(char) ?: 0)))
}

fun String.countChars(): Map<Char, Int> {
    return fold(emptyMap(), Map<Char, Int>::count)
}

fun Path.readLines(linesConsumer: (Sequence<String>) -> Unit): Unit {
    InputStreamReader(Files.newInputStream(this, StandardOpenOption.READ)).use { inputStreamReader ->
        BufferedReader(inputStreamReader).use { bufferedReader ->
            linesConsumer(bufferedReader.lineSequence())
        }
    }
}