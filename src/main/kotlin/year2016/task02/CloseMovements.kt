package year2016.task02

import common.Direction
import common.Position
import common.Direction.DOWN
import common.Direction.LEFT
import common.Direction.RIGHT
import common.Direction.UP
import common.IOUtils
import common.move

interface PasswordPanel {
    fun walk(initialPosition: Position, path: Path): String
}

class CustomPasswordPanel(private val buttons: Map<Position, Button>) : PasswordPanel {
    private fun hasButtonAt(position: Position): Boolean = buttons.containsKey(position)

    private fun buttonAt(position: Position): Button? = buttons[position]

    private fun move(initialPosition: Position, direction: Direction): Position {
        val positionAfterMove = initialPosition.move(direction)
        return when {
            hasButtonAt(positionAfterMove) -> positionAfterMove
            else -> initialPosition
        }
    }

    override fun walk(initialPosition: Position, path: Path): String {
        if (!hasButtonAt(initialPosition)) {
            throw IllegalArgumentException("Initial position must at some button")
        }
        return buttonAt(
                path.asSequence()
                        .fold(
                                initialPosition,
                                this::move
                        )
        )!!.symbol
    }
}

data class Button(val symbol: String)

class Path(private val list: List<Direction>) : Iterable<Direction> by list

fun String.toPath(): Path = Path(map(Char::parseDirection))

data class Bounds(val up: Int,
                  val right: Int,
                  val down: Int,
                  val left: Int)

fun Position.moveWithinBounds(b: Bounds, d: Direction): Position {
    return when (d) {
        Direction.UP -> if (b.up == y) this else move(d)
        Direction.RIGHT -> if (b.right == x) this else move(d)
        Direction.DOWN -> if (b.down == y) this else move(d)
        Direction.LEFT -> if (b.left == x) this else move(d)
    }
}

fun Char.parseDirection(): Direction {
    return when (this) {
        'U' -> UP
        'R' -> RIGHT
        'D' -> DOWN
        'L' -> LEFT
        else -> throw IllegalArgumentException("Unexpected char $this")
    }
}

object StandardPasswordPanel : PasswordPanel {
    private val upperBound = 1
    private val lowerBound = -1
    private val leftBound = -1
    private val rightBound = 1

    fun Position.toDigit(): Int {
        return when (this) {
            Position(-1, 1) -> 1
            Position(0, 1) -> 2
            Position(1, 1) -> 3
            Position(-1, 0) -> 4
            Position(0, 0) -> 5
            Position(1, 0) -> 6
            Position(-1, -1) -> 7
            Position(0, -1) -> 8
            Position(1, -1) -> 9
            else -> throw IllegalArgumentException("Unexpected position: $this")
        }
    }

    override fun walk(initialPosition: Position, path: Path): String {
        if (!(initialPosition.x in leftBound..rightBound && initialPosition.y in lowerBound..upperBound)) {
            throw IllegalArgumentException("Initial position must at some button")
        }
        return path.asSequence().fold(initialPosition) { p, m -> p.moveWithinBounds(bounds, m) }.toDigit().toString()
    }

    val bounds = Bounds(upperBound, rightBound, lowerBound, leftBound)
}

fun main(args: Array<String>) {
    val paths = IOUtils.useLinesFromResource("/year2016/task02/input.txt") { lines ->
        lines.map(String::toPath).toList()
    }
    val passwordPanelPasswordReader: (PasswordPanel, Position) -> String = { customPasswordPanel, initialPosition ->
        paths.map { path ->
            customPasswordPanel.walk(initialPosition, path)
        }.joinToString("")
    }
    println(passwordPanelPasswordReader(StandardPasswordPanel, Position(0, 0)))
    val firstCustomPasswordPanel = CustomPasswordPanel(mapOf(
            Position(1, 1) to Button("7"),
            Position(2, 1) to Button("8"),
            Position(3, 1) to Button("9"),
            Position(1, 2) to Button("4"),
            Position(2, 2) to Button("5"),
            Position(3, 2) to Button("6"),
            Position(1, 3) to Button("1"),
            Position(2, 3) to Button("2"),
            Position(3, 3) to Button("3")
    ))
    println(passwordPanelPasswordReader(firstCustomPasswordPanel, Position(2, 2)))
    val secondCustomPasswordPanel = CustomPasswordPanel(mapOf(
            Position(3, 1) to Button("D"),
            Position(2, 2) to Button("A"),
            Position(3, 2) to Button("B"),
            Position(4, 2) to Button("C"),
            Position(1, 3) to Button("5"),
            Position(2, 3) to Button("6"),
            Position(3, 3) to Button("7"),
            Position(4, 3) to Button("8"),
            Position(5, 3) to Button("9"),
            Position(2, 4) to Button("2"),
            Position(3, 4) to Button("3"),
            Position(4, 4) to Button("4"),
            Position(3, 5) to Button("1")
    ))
    println(passwordPanelPasswordReader(secondCustomPasswordPanel, Position(2, 2)))
}
