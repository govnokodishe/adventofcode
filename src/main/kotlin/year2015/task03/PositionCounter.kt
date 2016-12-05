package year2015.task03

import common.Direction
import common.Direction.DOWN
import common.Direction.LEFT
import common.Direction.RIGHT
import common.Direction.UP
import common.Position
import common.move
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

data class PositionCounter private constructor(private val pos: Position,
                                               val posMap: Map<Position, Int>) {
    constructor(startPos: Position) : this(startPos, mapOf(startPos to 1))

    fun move(d: Direction): PositionCounter {
        val newPos = pos.move(d)
        return PositionCounter(
                newPos,
                posMap.plus(newPos to (posMap[newPos]?.let { it + 1 } ?: 1))
        )
    }
}

fun Char.parseDirection(): Direction {
    return when (this) {
        '^' -> UP
        '>' -> RIGHT
        'v' -> DOWN
        '<' -> LEFT
        else -> throw IllegalArgumentException("Unexpected direction char $this")
    }
}

fun main(args: Array<String>) {
    println(
            String(
                    Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("year2015/task03/input.txt").toURI())),
                    StandardCharsets.UTF_8
            )
                    .map(Char::parseDirection)
                    .fold(PositionCounter(Position(0, 0)), PositionCounter::move)
                    .posMap.count()
    )
}
