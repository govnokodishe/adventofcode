package year2016.task12.computer.processor

interface InstructionNumber {
    fun asInteger(): Int
    fun jump(distance: Int): InstructionNumber
}