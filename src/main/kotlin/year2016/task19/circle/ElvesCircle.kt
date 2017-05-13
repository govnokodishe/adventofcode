package year2016.task19.circle

interface ElvesCircle {
    fun size(): Int
    operator fun get(index: Int): Int
    fun remove(index: Int): year2016.task19.circle.ElvesCircle
}