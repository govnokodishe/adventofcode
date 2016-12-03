package test02

import org.junit.Assert.assertEquals
import org.junit.Test
import common.Position
import year2016.task02.Bounds
import common.Direction.DOWN
import common.Direction.LEFT
import common.Direction.RIGHT
import common.Direction.UP
import year2016.task02.moveWithinBounds

class CloseMovementsTest {
    val bounds = Bounds(1,1,-1,-1)

    @Test
    fun testMoveUp() {
        assertEquals(
                Position(0, 1),
                Position(0, 0).moveWithinBounds(bounds, UP)
        )
    }

    @Test
    fun testMoveUpOnUpperBound() {
        assertEquals(
                Position(0, 1),
                Position(0, 1).moveWithinBounds(bounds, UP)
        )
    }

    @Test
    fun testMoveRight() {
        assertEquals(
                Position(1, 0),
                Position(0, 0).moveWithinBounds(bounds, RIGHT)
        )
    }

    @Test
    fun testMoveRightOnRightBound() {
        assertEquals(
                Position(1, 0),
                Position(1, 0).moveWithinBounds(bounds, RIGHT)
        )
    }

    @Test
    fun testMoveDown() {
        assertEquals(
                Position(0, -1),
                Position(0, 0).moveWithinBounds(bounds, DOWN)
        )
    }

    @Test
    fun testMoveDownOnDownBound() {
        assertEquals(
                Position(0, -1),
                Position(0, -1).moveWithinBounds(bounds, DOWN)
        )
    }

    @Test
    fun testMoveLeft() {
        assertEquals(
                Position(-1, 0),
                Position(0, 0).moveWithinBounds(bounds, LEFT)
        )
    }

    @Test
    fun testMoveLeftOnLeftBound() {
        assertEquals(
                Position(-1, 0),
                Position(-1, 0).moveWithinBounds(bounds, LEFT)
        )
    }
}
