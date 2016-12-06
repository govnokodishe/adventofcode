package year2016.task01

import common.Direction
import common.Position

enum class Rotation {
    LEFT,
    RIGHT,
}

data class Movement(val r: Rotation, val steps: Int)

data class DirectedPosition(val p: Position,
                            val d: Direction)

fun DirectedPosition.move(m: Movement): DirectedPosition {
    val newDirection = d.rotate(m.r)
    return DirectedPosition(p.move(m.steps, newDirection), newDirection)
}

fun Position.move(steps: Int, d: Direction): Position {
    return when (d) {
        Direction.UP -> Position(x, y + steps)
        Direction.RIGHT -> Position(x + steps, y)
        Direction.DOWN -> Position(x, y - steps)
        Direction.LEFT -> Position(x - steps, y)
    }
}

fun Direction.rotate(r: Rotation): Direction {
    return when (r) {
        Rotation.LEFT -> when (this) {
            Direction.UP -> Direction.LEFT
            Direction.RIGHT -> Direction.UP
            Direction.DOWN -> Direction.RIGHT
            Direction.LEFT -> Direction.DOWN
        }
        Rotation.RIGHT -> when (this) {
            Direction.UP -> Direction.RIGHT
            Direction.RIGHT -> Direction.DOWN
            Direction.DOWN -> Direction.LEFT
            Direction.LEFT -> Direction.UP
        }
    }
}

fun String.parseMovement(): Movement {
    if (this.length < 2) {
        throw IllegalArgumentException("Expected the length of str to be 2 or more")
    }
    return Movement(
            when (this[0]) {
                'L' -> Rotation.LEFT
                'R' -> Rotation.RIGHT
                else -> throw IllegalArgumentException("")
            },
            Integer.parseInt(this.substring(1))
    )
}

fun DirectedPosition.makeMovements(movements: Iterable<Movement>): DirectedPosition {
    return movements.fold(this, DirectedPosition::move)
}

fun Int.abs() = Math.abs(this)

fun Position.distance(p: Position): Int {
    return (this.x - p.x).abs() + (this.y - p.y).abs()
}


fun main(args: Array<String>) {
    val input = "R3, L5, R2, L2, R1, L3, R1, R3, L4, R3, L1, L1, R1, L3, R2, L3, L2, R1, R1, L1, R4, L1, L4, R3, L2, L2, R1, L1, R5, R4, R2, L5, L2, R5, R5, L2, R3, R1, R1, L3, R1, L4, L4, L190, L5, L2, R4, L5" +
            ", R4, R5, L4, R1, R2, L5, R50, L2, R1, R73, R1, L2, R191, R2, L4, R1, L5, L5, R5, L3, L5, L4, R4" +
            ", R5, L4, R4, R4, R5, L2, L5, R3, L4, L4, L5, R2, R2, R2, R4, L3, R4, R5, L3, R5, L2, R3, L1, R2" +
            ", R2, L3, L1, R5, L3, L5, R2, R4, R1, L1, L5, R3, R2, L3, L4, L5, L1, R3, L5, L2, R2, L3, L4, L1" +
            ", R1, R4, R2, R2, R4, R2, R2, L3, L3, L4, R4, L4, L4, R1, L4, L4, R1, L2, R5, R2, R3, R3, L2, L5" +
            ", R3, L3, R5, L2, R3, R2, L4, L3, L1, R2, L2, L3, L5, R3, L1, L3, L4, L3"
    val startPosition = DirectedPosition(Position(0, 0), Direction.UP)
    println(startPosition.makeMovements(input.split(", ").map(String::parseMovement)).p.distance(startPosition.p))
}