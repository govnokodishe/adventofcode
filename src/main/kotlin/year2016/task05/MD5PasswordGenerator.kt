package year2016.task05

import common.getFirstNHashesStartsWithZeros
import java.util.stream.Collectors.joining

fun generatePassword(id: String, size: Long): String {
    return getFirstNHashesStartsWithZeros(id, size, 5)
            .map { it.second[5].toString() }
            .collect(joining())
}

fun main(args: Array<String>) {
    println(generatePassword("ugkcyxxp", 8))
}
