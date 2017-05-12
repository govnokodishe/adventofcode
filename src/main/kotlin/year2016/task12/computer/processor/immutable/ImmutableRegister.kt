package year2016.task12.computer.processor.immutable

import year2016.task12.computer.processor.Register

class ImmutableRegister(private var value: Int) : Register {
    override fun readAsInteger(): Int = value

    override fun writeInteger(value: Int): Register = ImmutableRegister(value)
}