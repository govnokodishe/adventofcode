package task01

import common.Direction
import org.junit.Assert.assertEquals
import org.junit.Test
import year2016.task01.DirectedPosition
import year2016.task01.Movement
import common.Position
import year2016.task01.Rotation.LEFT
import year2016.task01.Rotation.RIGHT
import year2016.task01.makeMovements

class MovementsTest {
    @Test
    fun testTwoSteps() {
        assertEquals(
                DirectedPosition(Position(2, 3), Direction.UP),
                DirectedPosition(Position(0, 0), Direction.UP).makeMovements(listOf(
                        Movement(RIGHT, 2),
                        Movement(LEFT, 3)
                ))
        )
    }

    @Test
    fun testThreeSteps() {
        assertEquals(
                DirectedPosition(Position(0, -2), Direction.LEFT),
                DirectedPosition(Position(0, 0), Direction.UP).makeMovements(listOf(
                        Movement(RIGHT, 2),
                        Movement(RIGHT, 2),
                        Movement(RIGHT, 2)
                ))
        )
    }

    @Test
    fun testFourSteps() {
        assertEquals(
                DirectedPosition(Position(10, 2), Direction.DOWN),
                DirectedPosition(Position(0, 0), Direction.UP).makeMovements(listOf(
                        Movement(RIGHT, 5),
                        Movement(LEFT, 5),
                        Movement(RIGHT, 5),
                        Movement(RIGHT, 3)
                ))
        )
    }
}
