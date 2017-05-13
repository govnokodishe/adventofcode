package year2016.task19.circle

import java.util.*

class LinkedListElvesCircle private constructor(private val elvesCircle: MutableListElvesCircle) : ElvesCircle by elvesCircle {
    constructor(count: Int) : this(
            MutableListElvesCircle(
                    LinkedList<Int>().apply {
                        (1..count).forEach { element ->
                            this.add(element)
                        }
                    }
            )
    )
}
