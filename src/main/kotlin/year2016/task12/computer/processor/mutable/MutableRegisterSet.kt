package year2016.task12.computer.processor.mutable

import year2016.task12.computer.processor.Register
import year2016.task12.computer.processor.RegisterName
import year2016.task12.computer.processor.RegisterSet

class MutableRegisterSet(private val registers: MutableMap<String, Register>) : RegisterSet {
    constructor() : this(HashMap<String, Register>())

    override fun readValue(registerName: RegisterName): Int {
        return registers.computeIfAbsent(registerName.asString()) { _ ->
            MutableRegister(0)
        }.readAsInteger()
    }

    override fun writeValue(registerName: RegisterName, value: Int): RegisterSet {
        registers.put(registerName.asString(), MutableRegister(value))
        return this
    }
}
