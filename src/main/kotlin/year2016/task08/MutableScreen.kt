package year2016.task08

import common.Position
import year2015.task06.LightState
import year2015.task06.PositionsInRect

class MutableScreen private constructor(private val height: Int,
                                        private val width: Int,
                                        private val map: MutableMap<Position, LightState>) : Screen {
    constructor(height: Int, width: Int) : this(
            height,
            width,
            HashMap<Position, LightState>().fill(height, width, LightState.OFF)
    )

    companion object {
        private fun MutableMap<Position, LightState>.fill(height: Int,
                                                          width: Int,
                                                          lightState: LightState): MutableMap<Position, LightState> {
            return apply {
                PositionsInRect(height, width).map { position ->
                    put(position, lightState)
                }
            }
        }

        fun cols(width: Int): IntRange {
            return 0 until width
        }

        fun rows(height: Int): IntRange {
            return 0 until height
        }
    }

    private fun cols(): IntRange {
        return cols(width)
    }

    private fun rows(): IntRange {
        return cols(height)
    }

    private fun Int.nextCircled(max: Int): Int = when (this) {
        0 -> max
        else -> this - 1
    }

    private fun Int.prevRow(): Int = nextCircled(height - 1)

    private fun Int.prevCol(): Int = nextCircled(width - 1)

    override fun width(): Int {
        return width
    }

    override fun height(): Int {
        return height
    }

    override fun liteUp(height: Int, width: Int): Screen = this.apply {
        map.fill(height, width, LightState.ON)
    }

    override fun rotateCol(col: Int): Screen = this.apply {
        val rows = rows()
        val lightStatesAtCol = rows.map { row ->
            row to lightStateAt(Position(row, col))
        }.toMap()
        rows.forEach { row ->
            map.put(Position(row, col), lightStatesAtCol[row.prevRow()]!!)
        }
    }

    override fun rotateRow(row: Int): Screen = this.apply {
        val cols = cols()
        val lightStatesAtRow = cols.map { col ->
            col to lightStateAt(Position(row, col))
        }.toMap()
        cols.forEach { col ->
            map.put(Position(row, col), lightStatesAtRow[col.prevCol()]!!)
        }
    }

    override fun lightStateAt(position: Position): LightState {
        return map[position] ?: throw IllegalArgumentException("Position is out of range: $position")
    }
}

