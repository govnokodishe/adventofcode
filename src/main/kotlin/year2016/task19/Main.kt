package year2016.task19

fun elfWithAllPresents(count: Int): Int {
    val elfesWithPresent: MutableSet<Int> = HashSet<Int>().apply {
        (1..count).forEach { add(it) }
    }

    tailrec fun nextElf(currentElf: Int): Int {
        val nextElfCandidate = when (currentElf) {
            count -> 1
            else -> currentElf + 1
        }
        return when {
            elfesWithPresent.contains(nextElfCandidate) -> nextElfCandidate
            else -> nextElf(nextElfCandidate)
        }
    }

    while (elfesWithPresent.size > 1) {
        var currentElf = 1
        do {
            if (elfesWithPresent.contains(currentElf)) {
                val nextElf = nextElf(currentElf)
                elfesWithPresent.remove(nextElf)
            }
            currentElf += 1
        } while (currentElf <= count && elfesWithPresent.size > 1)
    }
    return elfesWithPresent.iterator().next()
}

fun main(args: Array<String>) {
    println(elfWithAllPresents(3001330))
}
