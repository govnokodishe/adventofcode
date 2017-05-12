package year2016.task12

import year2016.task12.computer.Computer
import year2016.task12.computer.program.instruction.factory.InstructionFactory
import year2016.task12.computer.program.ArrayListBasedProgram
import year2016.task12.computer.processor.Processor
import year2016.task12.computer.processor.RegisterNames
import year2016.task12.computer.processor.immutable.ImmutableProcessor
import year2016.task12.computer.processor.mutable.MutableProcessor
import year2016.task12.runner.UntilHaltedComputerRunner
import java.io.InputStreamReader
import java.io.Reader

object Main {
    val program = ArrayListBasedProgram(
            javaClass.getResourceAsStream("/year2016/task12/input.txt").use { inputStream ->
                InputStreamReader(inputStream).use { reader: Reader ->
                    reader.useLines { lines ->
                        lines.map(InstructionFactory::fromString).toList()
                    }
                }
            }
    )

    fun run(processorFactory: () -> Processor) {
        val computer = UntilHaltedComputerRunner(Computer(processorFactory().writeRegister(RegisterNames.C, 1), program)).run()
        println("a: ${computer.peek(RegisterNames.A)}")
    }
}

fun main(args: Array<String>) {
    Main.run(::MutableProcessor)
    Main.run(::ImmutableProcessor)
}
