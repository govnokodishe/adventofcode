package year2016.task12.computer.program;

import year2016.task12.computer.program.instruction.Instruction;

interface Program {
    fun instructionAt(position: Int): Instruction
    fun lastInstructionPosition(): Int
}
