package year2015.task06

import common.IOUtils
import common.Position
import year2015.task06.LightState.OFF
import java.util.*

class Display private constructor(private val width: Int,
                                  private val height: Int,
                                  private val lights: Map<Position, LightState>) {
    constructor(width: Int, height: Int) : this(width,
                                                height,
                                                (0 until width).flatMap(0 until height).map { it to OFF }.toMap())

    companion object {
        private fun LightState.afterCommand(command: LightStateCommand): LightState {
            return when (command) {
                LightStateCommand.TURN_ON -> LightState.ON
                LightStateCommand.TURN_OFF -> LightState.OFF
                LightStateCommand.TOGGLE -> when (this) {
                    LightState.ON -> LightState.OFF
                    LightState.OFF -> LightState.ON
                }
            }
        }
    }

    fun turnedOnPixelsCount(): Int = lights.values.count { lightState -> LightState.ON == lightState }

    fun execute(command: DisplayCommand): Display {
        val newLights: MutableMap<Position, LightState> = HashMap(lights)
        (command.startPosition.x..command.endPosition.x).flatMap(command.startPosition.y..command.endPosition.y).forEach { pos ->
            newLights.put(pos, lights[pos]?.afterCommand(command.lightStateCommand) ?: throw IndexOutOfBoundsException("Display size is ($width,$height), but command for (${pos.x},${pos.y}) was given"))
        }
        return Display(width, height, newLights)
    }
}

class DisplayWithBrightness private constructor(val width: Int,
                                                val height: Int,
                                                val lights: Map<Position, Int>) {
    constructor(width: Int, height: Int) : this(width,
                                                height,
                                                (0 until width).flatMap(0 until height).map { it to 0 }.toMap())

    companion object {
        private fun Int.afterCommand(lightStateCommand: LightStateCommand): Int = when (lightStateCommand) {
            LightStateCommand.TURN_ON -> this + 1
            LightStateCommand.TURN_OFF -> when (this) {
                0 -> 0
                else -> this - 1
            }
            LightStateCommand.TOGGLE -> this + 2
        }
    }

    fun totalBrightness(): Int = lights.values.sum()

    fun execute(command: DisplayCommand): DisplayWithBrightness {
        val newLights: MutableMap<Position, Int> = HashMap(lights)
        (command.startPosition.x..command.endPosition.x).flatMap(command.startPosition.y..command.endPosition.y).forEach { pos ->
            newLights.put(pos,
                          lights[pos]?.afterCommand(command.lightStateCommand) ?: throw IndexOutOfBoundsException("Display size is ($width,$height), but command for (${pos.x},${pos.y}) was given"))
        }
        return DisplayWithBrightness(width, height, newLights)
    }
}

fun IntRange.flatMap(range: IntRange): Iterable<Position> {
    return this.flatMap { x -> (range).map { y -> Position(x, y) } }
}

class PositionsInRect(height: Int, width: Int): Iterable<Position> {
    private val positions: Iterable<Position> by lazy { (0 until height).flatMap(0 until width) }
    override fun iterator(): Iterator<Position> {
        return positions.iterator()
    }
}

enum class LightState {
    ON,
    OFF,
}

enum class LightStateCommand {
    TURN_ON,
    TURN_OFF,
    TOGGLE,
}

data class DisplayCommand(val lightStateCommand: LightStateCommand,
                          val startPosition: Position,
                          val endPosition: Position)

private val turnOnStr = "turn on"
private val turnOffStr = "turn off"
private val toggleStr = "toggle"

private val mapping = mapOf(
        turnOnStr to LightStateCommand.TURN_ON,
        turnOffStr to LightStateCommand.TURN_OFF,
        toggleStr to LightStateCommand.TOGGLE
)

fun String.parseDisplayCommand(): DisplayCommand {
    fun String.parsePositions(): Pair<Position, Position> {
        fun String.parsePosition(): Position {
            val parts = split(",")
            return Position(parts[0].toInt(), parts[1].toInt())
        }

        val parts = split(" through ")
        return parts[0].parsePosition() to parts[1].parsePosition()
    }

    return mapping.entries.firstOrNull { startsWith(it.key) }?.let {
        val lightStateCommand = it.value
        val (startPos, endPos) = substring(it.key.length + 1).parsePositions()
        DisplayCommand(lightStateCommand, startPos, endPos)
    } ?: throw IllegalArgumentException("Unexpected beginning of the command")
}

fun main(args: Array<String>) {
    IOUtils.useLinesFromResource("/year2015/task06/input.txt") { lines ->
        println(
                lines
                        .map(String::trim)
                        .map(String::parseDisplayCommand)
                        .fold(Display(1000, 1000), Display::execute)
                        .turnedOnPixelsCount()
        )
    }
    IOUtils.useLinesFromResource("/year2015/task06/input.txt") { lines ->
        println(
                lines
                        .map(String::trim)
                        .map(String::parseDisplayCommand)
                        .fold(DisplayWithBrightness(1000, 1000), DisplayWithBrightness::execute)
                        .totalBrightness()
        )
    }
}
