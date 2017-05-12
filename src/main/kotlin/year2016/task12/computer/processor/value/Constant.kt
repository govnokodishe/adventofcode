package year2016.task12.computer.processor.value

import year2016.task12.computer.processor.Processor

class Constant(private val value: Int) : Value {
    override fun get(processor: Processor): Int = value
}