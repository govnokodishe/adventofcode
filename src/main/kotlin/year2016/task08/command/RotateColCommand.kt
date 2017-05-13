package year2016.task08.command

import year2016.task08.Screen

data class RotateColCommand(private val col: Int, private val timesCount: Int): Command {
    override fun execute(screen: Screen): Screen = (0 until timesCount).fold(screen) {
        screen, _ -> screen.rotateCol(col)
    }
}
