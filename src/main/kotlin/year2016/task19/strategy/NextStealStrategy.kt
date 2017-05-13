package year2016.task19.strategy

object NextStealStrategy : StealStrategy {
    override fun elfPositionToStealFrom(currentElfPosition: Int, numberOfElves: Int): Int {
        return when (currentElfPosition) {
            numberOfElves - 1 -> 0
            else -> currentElfPosition + 1
        }
    }
}