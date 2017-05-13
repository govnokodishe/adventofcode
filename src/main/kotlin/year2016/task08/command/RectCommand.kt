package year2016.task08.command

import year2016.task08.Screen

data class RectCommand(private val height: Int, private val width: Int) : Command {
    override fun execute(screen: Screen): Screen = screen.liteUp(height, width)
}
