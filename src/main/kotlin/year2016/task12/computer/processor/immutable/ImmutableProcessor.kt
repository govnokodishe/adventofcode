package year2016.task12.computer.processor.immutable

import year2016.task12.computer.processor.InstructionNumber
import year2016.task12.computer.processor.Processor
import year2016.task12.computer.processor.Register
import year2016.task12.computer.processor.RegisterName

class ImmutableProcessor private constructor(
        private val registers: Map<String, Register>,
        private val instructionNumber: InstructionNumber
) : Processor {
    constructor() : this(
            emptyMap<String, Register>(),
            ImmutableInstructionNumber(0)
    )

    override fun readRegister(registerName: RegisterName): Int = registers[registerName.asString()]?.readAsInteger() ?: 0

    override fun writeRegister(registerName: RegisterName, value: Int): Processor = ImmutableProcessor(
            registers.plus(registerName.asString() to ImmutableRegister(value)),
            instructionNumber
    )

    override fun position(): Int = instructionNumber.asInteger()

    override fun jump(distance: Int): Processor = ImmutableProcessor(
            registers,
            instructionNumber.jump(distance)
    )
}
