package year2016.task08

import year2016.task08.command.Command
import year2016.task08.command.RectCommand
import year2016.task08.command.RotateColCommand
import year2016.task08.command.RotateRowCommand

object StringCommandFactory {
    fun command(strCommand: String): Command = when {
        strCommand.startsWith("rect ") -> strCommand.split(" ")[1].split("x").let { arr ->
            RectCommand(arr[1].toInt(), arr[0].toInt())
        }
        strCommand.startsWith("rotate column ") -> {
            val arr = strCommand.split(" ")
            RotateColCommand(
                    arr[2].substring(2).toInt(),
                    arr[4].toInt()
            )
        }
        strCommand.startsWith("rotate row ") -> {
            val arr = strCommand.split(" ")
            RotateRowCommand(
                    arr[2].substring(2).toInt(),
                    arr[4].toInt()
            )
        }
        else -> throw IllegalArgumentException("Unknown command $this")
    }
}