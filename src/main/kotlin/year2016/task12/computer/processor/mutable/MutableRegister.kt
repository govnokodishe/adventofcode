package year2016.task12.computer.processor.mutable

import year2016.task12.computer.processor.Register

class MutableRegister(private var value: Int) : Register {
    override fun readAsInteger(): Int = value

    override fun writeInteger(value: Int): Register {
        this.value = value
        return this
    }
}