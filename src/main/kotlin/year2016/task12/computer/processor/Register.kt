package year2016.task12.computer.processor

interface Register {
    fun readAsInteger(): Int
    fun writeInteger(value: Int): Register
}