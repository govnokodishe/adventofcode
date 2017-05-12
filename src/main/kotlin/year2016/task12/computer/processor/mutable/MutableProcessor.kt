package year2016.task12.computer.processor.mutable

import year2016.task12.computer.processor.InstructionNumber
import year2016.task12.computer.processor.Processor
import year2016.task12.computer.processor.Register
import year2016.task12.computer.processor.RegisterName
import year2016.task12.computer.processor.value.Value

class MutableProcessor private constructor(
        private val registers: MutableMap<String, Register>,
        private val instructionNumber: InstructionNumber
) : Processor {
    constructor() : this(
            HashMap<String, Register>(),
            MutableInstructionNumber(0)
    )

    override fun readRegister(registerName: RegisterName): Int {
        return registers.computeIfAbsent(registerName.asString()) { _ ->
            MutableRegister(0)
        }.readAsInteger()
    }

    override fun writeRegister(registerName: RegisterName, value: Int): Processor {
        registers.put(registerName.asString(), MutableRegister(value))
        return this
    }

    override fun position(): Int = instructionNumber.asInteger()

    override fun jump(distance: Int): Processor {
        instructionNumber.jump(distance)
        return this
    }
}
