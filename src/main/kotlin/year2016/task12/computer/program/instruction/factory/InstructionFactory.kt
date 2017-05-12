package year2016.task12.computer.program.instruction.factory

import year2016.task12.computer.processor.RegisterName
import year2016.task12.computer.processor.RegisterNames
import year2016.task12.computer.processor.value.Constant
import year2016.task12.computer.processor.value.RegisterValue
import year2016.task12.computer.processor.value.Value
import year2016.task12.computer.program.instruction.Copy
import year2016.task12.computer.program.instruction.Decrement
import year2016.task12.computer.program.instruction.Increment
import year2016.task12.computer.program.instruction.Instruction
import year2016.task12.computer.program.instruction.JumpIfNonZero
import java.util.regex.Pattern

object InstructionFactory {
    internal object ValueFactory {
        private val registerPatternTester = Pattern.compile("\\p{Alpha}").asPredicate()

        fun fromString(str: String): Value = when {
            registerPatternTester.test(str) -> RegisterValue(RegisterNameFactory.fromString(str))
            else -> Constant(str.toInt())
        }
    }

    internal object RegisterNameFactory {
        fun fromString(str: String): RegisterName = when (str) {
            "a" -> RegisterNames.A
            "b" -> RegisterNames.B
            "c" -> RegisterNames.C
            "d" -> RegisterNames.D
            else -> throw IllegalArgumentException("No register with name $str")
        }
    }

    fun fromString(str: String): Instruction = when {
        str.startsWith("cpy ") -> {
            val split = str.split(" ")
            Copy(
                    ValueFactory.fromString(split[1]),
                    RegisterNameFactory.fromString(split[2])
            )
        }
        str.startsWith("inc ") -> {
            val split = str.split(" ")
            Increment(RegisterNameFactory.fromString(split[1]))
        }
        str.startsWith("dec ") -> {
            val split = str.split(" ")
            Decrement(RegisterNameFactory.fromString(split[1]))
        }
        str.startsWith("jnz ") -> {
            val split = str.split(" ")
            JumpIfNonZero(
                    ValueFactory.fromString(split[1]),
                    split[2].toInt()
            )
        }
        else -> throw IllegalArgumentException("Unknown instruction $str")
    }
}
