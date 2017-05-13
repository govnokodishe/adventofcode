package year2016.task19.circle

class ListElvesCircle private constructor(private val list: List<Int>) : ElvesCircle {
    constructor(count: Int) : this((1..count).toList())

    companion object {
        private fun <T> List<T>.removeAt(index: Int): List<T> = subList(0, index).plus(subList(index + 1, size))
    }

    override fun size(): Int = list.size
    override operator fun get(index: Int): Int = list[index]
    override fun remove(index: Int): ElvesCircle = ListElvesCircle(list.removeAt(index))
}