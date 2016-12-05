package common

import common.Direction.DOWN
import common.Direction.LEFT
import common.Direction.RIGHT
import common.Direction.UP

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