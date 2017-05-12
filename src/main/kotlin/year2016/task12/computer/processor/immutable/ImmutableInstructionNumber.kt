package year2016.task12.computer.processor.immutable

import com.google.common.base.Preconditions.checkArgument
import year2016.task12.computer.processor.InstructionNumber

class ImmutableInstructionNumber(val number: Int) : InstructionNumber {
    init {
        checkArgument(number >= 0, "Number must be non negative integer, but got $number")
    }

    override fun asInteger(): Int = number

    override fun jump(distance: Int): InstructionNumber = when (distance) {
        0 -> this
        else -> ImmutableInstructionNumber(number + distance)
    }
}