package year2016.task08.printer

import common.Position
import year2015.task06.LightState
import year2016.task08.Screen

class DefaultScreenPrinter constructor(private val screen: Screen,
                                       private val symbolAsCharFun: (LightState) -> Char) : ScreenPrinter {
    constructor(screen: Screen) : this(
            screen,
            { lightState ->
                when (lightState) {
                    LightState.ON -> '#'
                    LightState.OFF -> '.'
                }
            }
    )

    override fun asString(): String {
        val height = screen.height()
        val width = screen.width()
        val stringBuilder = StringBuilder(height * width)
        val cols = 0 until width
        (0 until height).forEach { row ->
            cols
                    .map { col -> Position(row, col) }
                    .map(screen::lightStateAt)
                    .map(symbolAsCharFun)
                    .forEach { char -> stringBuilder.append(char) }
            stringBuilder.append(System.lineSeparator())
        }
        return stringBuilder.toString()
    }
}