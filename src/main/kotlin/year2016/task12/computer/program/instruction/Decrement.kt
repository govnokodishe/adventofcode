package year2016.task12.computer.program.instruction

import year2016.task12.computer.processor.Processor
import year2016.task12.computer.processor.RegisterName
import year2016.task12.computer.processor.value.RegisterValue

class Decrement(private val registerName: RegisterName) : Instruction {
    override fun execute(processor: Processor): Processor {
        return processor.writeRegister(
                registerName,
                RegisterValue(registerName).get(processor) - 1
        ).jump(1)
    }
}