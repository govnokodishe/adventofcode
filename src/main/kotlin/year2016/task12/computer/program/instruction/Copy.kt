package year2016.task12.computer.program.instruction

import year2016.task12.computer.processor.Processor
import year2016.task12.computer.processor.RegisterName
import year2016.task12.computer.processor.value.Value

class Copy(private val value: Value, private val registerName: RegisterName) : Instruction {
    override fun execute(processor: Processor): Processor {
        return processor.writeRegister(
                registerName,
                value.get(processor)
        ).jump(1)
    }
}