package year2016.task05

import common.getFirstNHashesStartsWithFiveZeros
import java.util.stream.Collectors.joining

fun generatePassword(id: String, size: Long): String {
    return getFirstNHashesStartsWithFiveZeros(id, size)
            .map { it.second[5].toString() }
            .collect(joining())
}

fun main(args: Array<String>) {
    println(generatePassword("ugkcyxxp", 8))
}
