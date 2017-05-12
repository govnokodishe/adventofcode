package year2016.task12.computer.processor.value

import year2016.task12.computer.processor.Processor

interface Value {
    fun get(processor: Processor): Int
}