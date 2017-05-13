package year2016.task19

import year2016.task19.circle.ArrayListElvesCircle
import year2016.task19.circle.ElvesCircle
import year2016.task19.circle.VectorElvesCircle
import year2016.task19.strategy.AcrossTheCircleStealStrategy
import year2016.task19.strategy.NextStealStrategy
import year2016.task19.strategy.StealStrategy
import java.time.LocalDateTime

typealias ElvesCircleFactory = (Int) -> ElvesCircle

fun elfWithAllPresents(numberOfElves: Int,
                       stealStrategy: StealStrategy,
                       elvesCircleFactory: ElvesCircleFactory): Int {
    tailrec fun elfWithAllPresents(currentElfPosition: Int,
                                   elvesCircle: ElvesCircle): Int {
        val elvesCount = elvesCircle.size()
        if (elvesCount % 10000 == 0) {
            println("${LocalDateTime.now()}: $elvesCount")
        }
        return when (elvesCount) {
            1 -> elvesCircle[0]
            else -> {
                val elfPositionToStealFrom = stealStrategy.elfPositionToStealFrom(currentElfPosition, elvesCount)
                val modifiedElvesCircle = elvesCircle.remove(elfPositionToStealFrom)
                val nextElfPosition = when (currentElfPosition) {
                    in (0 until modifiedElvesCircle.size() - 1) -> currentElfPosition + 1
                    else -> 0
                }
                elfWithAllPresents(nextElfPosition, modifiedElvesCircle)
            }
        }
    }
    return elfWithAllPresents(0, elvesCircleFactory(numberOfElves))
}

fun main(args: Array<String>) {
    val numberOfElves = 3001330
    println(elfWithAllPresents(numberOfElves, AcrossTheCircleStealStrategy, ::VectorElvesCircle))
    println(elfWithAllPresents(numberOfElves, NextStealStrategy, ::ArrayListElvesCircle))
}
