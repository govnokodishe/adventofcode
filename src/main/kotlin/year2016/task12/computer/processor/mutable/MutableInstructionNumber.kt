package year2016.task12.computer.processor.mutable

import com.google.common.base.Preconditions.checkArgument
import year2016.task12.computer.processor.InstructionNumber

class MutableInstructionNumber(var number: Int) : InstructionNumber {
    init {
        check(number)
    }
    private companion object {
        private fun check(newNumber: Int): Int {
            checkArgument(newNumber >= 0, "Number must be non negative integer, but got $newNumber")
            return newNumber
        }
    }

    override fun asInteger(): Int = number

    override fun jump(distance: Int): InstructionNumber {
        if (distance != 0) {
            number = check(number + distance)
        }
        return this
    }
}