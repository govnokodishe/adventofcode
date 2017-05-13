package year2016.task08.command

import year2016.task08.Screen

interface Command {
    fun execute(screen: Screen): Screen
}
