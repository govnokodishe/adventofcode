package year2016.task19.circle

internal class MutableListElvesCircle constructor(private val list: MutableList<Int>) : ElvesCircle {
    override fun size(): Int = list.size
    override fun get(index: Int): Int = list[index]
    override fun remove(index: Int): ElvesCircle = apply { list.removeAt(index) }
}
