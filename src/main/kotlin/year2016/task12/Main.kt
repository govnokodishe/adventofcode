package year2016.task12

import common.IOUtils
import year2016.task12.computer.Computer
import year2016.task12.computer.processor.Processor
import year2016.task12.computer.processor.RegisterNames
import year2016.task12.computer.processor.immutable.ImmutableProcessor
import year2016.task12.computer.processor.mutable.MutableProcessor
import year2016.task12.computer.program.ArrayListBasedProgram
import year2016.task12.computer.program.instruction.factory.InstructionFactory
import year2016.task12.runner.UntilHaltedComputerRunner

object Main {
    val program = ArrayListBasedProgram(
            IOUtils.useLinesFromResource("/year2016/task12/input.txt") { lines ->
                lines.map(InstructionFactory::fromString).toList()
            }
    )

    fun run(processorFactory: () -> Processor) {
        val computer = UntilHaltedComputerRunner(Computer(processorFactory().writeRegister(RegisterNames.C, 1),
                                                          program)).run()
        println("a: ${computer.peek(RegisterNames.A)}")
    }
}

fun main(args: Array<String>) {
    Main.run(::MutableProcessor)
    Main.run(::ImmutableProcessor)
}
