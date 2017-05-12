package year2016.task12.runner

import year2016.task12.computer.Computer
import year2016.task12.computer.ComputerHaltedException

class UntilHaltedComputerRunner(private val computer: Computer) : ComputerRunner {
    override fun run(): Computer {
        try {
            while (true) {
                computer.step()
            }
        } catch (ignored: ComputerHaltedException) {
        }
        return computer
    }
}