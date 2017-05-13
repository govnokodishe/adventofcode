package year2016.task08

import common.IOUtils
import year2015.task06.LightState
import year2015.task06.PositionsInRect
import year2016.task08.printer.DefaultScreenPrinter

fun main(args: Array<String>) {
    val width = 50
    val height = 6
    val screen = IOUtils.useLinesFromResource("/year2016/task08/input.txt") { lines ->
        lines
                .map(StringCommandFactory::command)
                .fold(MutableScreen(height, width) as Screen) { screen, command ->
                    val modifiedScreen = command.execute(screen)
                    println(command)
                    println(DefaultScreenPrinter(modifiedScreen).asString())
                    modifiedScreen
                }
    }
    println(
            PositionsInRect(height, width)
                    .asSequence()
                    .map(screen::lightStateAt)
                    .count { lightState -> LightState.ON == lightState }
    )
    println(
            DefaultScreenPrinter(screen) { lightState ->
                when (lightState) {
                    LightState.ON -> '#'
                    LightState.OFF -> ' '
                }
            }.asString()
    )
}
