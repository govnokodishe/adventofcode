package year2016.task08.command

import year2016.task08.Screen

data class RotateRowCommand(private val row: Int, private val timesCount: Int): Command {
    override fun execute(screen: Screen): Screen = (0 until timesCount).fold(screen) {
        screen, _ -> screen.rotateRow(row)
    }
}
