package year2015.task06

import common.Position
import rx.Observable
import year2015.task06.LightState.OFF
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.stream.Stream

data class Display private constructor(val width: Int,
                                       val height: Int,
                                       val lights: Map<Position, LightState>) {
    constructor(width: Int, height: Int) : this(width, height, (0..width - 1).flatMap(0..height - 1).map { it to OFF }.toMap())

    fun execute(command: DisplayCommand): Display {
        val newLights: MutableMap<Position, LightState> = HashMap(lights)
        (command.startPosition.x..command.endPosition.x).flatMap(command.startPosition.y..command.endPosition.y).forEach { pos ->
            newLights.put(pos, lights[pos]?.afterCommand(command.lightStateCommand) ?: throw IndexOutOfBoundsException("Display size is ($width,$height), but command for (${pos.x},${pos.y}) was given"))
        }
        return Display(width, height, newLights)
    }
}

fun IntRange.flatMap(range: IntRange): Iterable<Position> {
    return this.flatMap { x -> (range).map { y -> Position(x, y) } }
}

fun LightState.afterCommand(command: LightStateCommand): LightState {
    return when (command) {
        LightStateCommand.TURN_ON -> LightState.ON
        LightStateCommand.TURN_OFF -> LightState.OFF
        LightStateCommand.TOGGLE -> when (this) {
            LightState.ON -> LightState.OFF
            LightState.OFF -> LightState.ON
        }
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

fun <T> Stream<T>.toObservable(): Observable<T> {
    return Observable.from(Iterable { this.iterator() })
}

fun main(args: Array<String>) {
    Files.lines(Paths.get(ClassLoader.getSystemResource("year2015/task06/input.txt").toURI()))
            .map(String::trim)
            .map(String::parseDisplayCommand)
            .toObservable()
            .reduce(Display(1000, 1000), Display::execute)
            .first().toSingle()
            .subscribe { display ->
                println(display.lights.values.count { it == LightState.ON })
            }
}
