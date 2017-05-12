package year2016.task12.computer

import year2016.task12.computer.processor.Processor
import year2016.task12.computer.processor.RegisterName
import year2016.task12.computer.program.Program

class Computer(private var processor: Processor, private val program: Program) {
    fun step(): Unit = with(processor) {
        val position = position()
        val lastInstructionPosition = program.lastInstructionPosition()
        if (position > lastInstructionPosition) {
            throw ComputerHaltedException("Position $position is out of range. Max position is $lastInstructionPosition.")
        }
        processor = program.instructionAt(position).execute(this)
    }

    fun peek(registerName: RegisterName): Int = processor.readRegister(registerName)
}
