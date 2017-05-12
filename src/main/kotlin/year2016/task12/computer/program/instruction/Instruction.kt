package year2016.task12.computer.program.instruction

import year2016.task12.computer.processor.Processor

interface Instruction {
    fun execute(processor: Processor): Processor
}

