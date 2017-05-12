package year2016.task12.computer.processor.value

import year2016.task12.computer.processor.Processor
import year2016.task12.computer.processor.RegisterName

class RegisterValue(private val registerName: RegisterName) : Value {
    override fun get(processor: Processor): Int = processor.readRegister(registerName)
}