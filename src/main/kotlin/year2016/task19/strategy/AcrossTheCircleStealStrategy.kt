package year2016.task19.strategy

object AcrossTheCircleStealStrategy : StealStrategy {
    override fun elfPositionToStealFrom(currentElfPosition: Int, numberOfElves: Int): Int {
        return (currentElfPosition + (numberOfElves / 2)) % numberOfElves
    }
}