package year2016.task12.computer.processor

interface RegisterSet {
    fun readValue(registerName: RegisterName): Int
    fun writeValue(registerName: RegisterName, value: Int): RegisterSet
}
