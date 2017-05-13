package year2016.task19.strategy

interface StealStrategy {
    fun elfPositionToStealFrom(currentElfPosition: Int, numberOfElves: Int): Int
}