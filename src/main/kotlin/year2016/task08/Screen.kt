package year2016.task08

import common.Position
import year2015.task06.LightState

interface Screen {
    fun width(): Int
    fun height(): Int
    fun liteUp(height: Int, width: Int): Screen
    fun rotateCol(col: Int): Screen
    fun rotateRow(row: Int): Screen
    fun lightStateAt(position: Position): LightState
}
