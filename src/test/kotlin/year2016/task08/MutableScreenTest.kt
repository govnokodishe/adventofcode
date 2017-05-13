package year2016.task08

import common.Position
import org.junit.Assert.assertEquals
import org.junit.Test
import year2015.task06.LightState
import year2015.task06.PositionsInRect
import year2016.task08.command.RectCommand
import year2016.task08.command.RotateColCommand
import year2016.task08.command.RotateRowCommand

open class MutableScreenTest {
    @Test
    fun testRect() {
        val width = 7
        val height = 3
        val onWidth = 3
        val onHeight = 2
        val screen = MutableScreen(height, width).apply { RectCommand(onHeight,
                                                                                              onWidth).execute(this) }
        testScreenState(
                screen,
                PositionsInRect(onHeight, onWidth)
        )
    }

    @Test
    fun testRotateCol() {
        val screen = MutableScreen(3, 7)
                .liteUp(2, 3)
                .apply { RotateColCommand(1, 1).execute(this) }
        testScreenState(
                screen,
                setOf(
                        Position(0, 0), Position(0, 2),
                        Position(1, 0), Position(1, 1), Position(1, 2),
                        Position(2, 1)
                )
        )
    }

    @Test
    fun testRotateRow() {
        val screen = MutableScreen(3, 7)
                .liteUp(2, 3)
                .apply { RotateColCommand(1, 1).execute(this) }
                .apply { RotateRowCommand(0, 5).execute(this) }
        testScreenState(
                screen,
                setOf(
                        Position(0, 0), Position(0, 5),
                        Position(1, 0), Position(1, 1), Position(1, 2),
                        Position(2, 1)
                )
        )
    }

    fun testScreenState(screen: Screen, turnedOnPositions: Iterable<Position>) {
        turnedOnPositions.forEach { position ->
            assertEquals("$position has wrong light state", LightState.ON, screen.lightStateAt(position))
        }
        val turnedOffPositions = PositionsInRect(screen.height(), screen.width()).filter { position ->
            !turnedOnPositions.contains(position)
        }
        turnedOffPositions.forEach { position ->
            assertEquals("$position has wrong light state", LightState.OFF, screen.lightStateAt(position))
        }
    }
}