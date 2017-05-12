package year2016.task12.computer.program.instruction

import year2016.task12.computer.processor.Processor
import year2016.task12.computer.processor.value.Value

class JumpIfNonZero(private val value: Value, private val distance: Int) : Instruction {
    override fun execute(processor: Processor): Processor {
        return when (value.get(processor)) {
            0 -> processor.jump(1)
            else -> processor.jump(distance)
        }
    }
}