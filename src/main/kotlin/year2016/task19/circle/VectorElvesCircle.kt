package year2016.task19.circle

import java.util.*

class VectorElvesCircle private constructor(private val elvesCircle: MutableListElvesCircle) : ElvesCircle by elvesCircle {
    constructor(count: Int) : this(
            MutableListElvesCircle(
                    Vector<Int>(count).apply {
                        (1..count).forEach { element ->
                            this.add(element)
                        }
                    }
            )
    )
}

