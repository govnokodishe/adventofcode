package year2016.task12.computer.program

import year2016.task12.computer.program.instruction.Instruction

class ArrayListBasedProgram private constructor(val instructions: ArrayList<Instruction>) : Program {
    constructor(instructions: Collection<Instruction>) : this(ArrayList(instructions))

    override fun instructionAt(position: Int): Instruction = instructions[position]

    override fun lastInstructionPosition(): Int = instructions.size - 1
}