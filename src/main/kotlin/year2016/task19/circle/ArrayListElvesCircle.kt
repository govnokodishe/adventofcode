package year2016.task19.circle

class ArrayListElvesCircle private constructor(private val elvesCircle: MutableListElvesCircle) : ElvesCircle by elvesCircle {
    constructor(count: Int) : this(
            MutableListElvesCircle(
                    ArrayList<Int>(count).apply {
                        (1..count).forEach { element ->
                            this.add(element)
                        }
                    }
            )
    )
}