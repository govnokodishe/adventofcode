package year2016.task12.computer.processor

interface Processor {
    fun readRegister(registerName: RegisterName): Int
    fun writeRegister(registerName: RegisterName, value: Int): Processor
    fun position(): Int
    fun jump(distance: Int): Processor
}

