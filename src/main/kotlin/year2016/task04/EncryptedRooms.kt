package year2016.task04

import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

data class EncryptedRoom(val encryptedGame: List<String>,
                         val id: Int,
                         val checksum: String)

fun String.countChars(): Map<Char, Int> {
    fun Map<Char, Int>.count(char: Char): Map<Char, Int> {
        return plus(char to (1 + (get(char) ?: 0)))
    }
    return fold(emptyMap(), Map<Char, Int>::count)
}

fun List<String>.checkSum(): String {
    return reduce(String::plus).countChars().entries.sortedWith(Comparator { p1, p2 ->
        when {
            p1.value > p2.value -> -1
            p1.value < p2.value -> 1
            else -> p1.key.toLowerCase().toInt().compareTo(p2.key.toLowerCase().toInt())
        }
    })
            .subList(0, 5)
            .map(Map.Entry<Char, Int>::key)
            .fold("", String::plus)
}

fun EncryptedRoom.isReal(): Boolean {
    return encryptedGame.checkSum() == checksum
}

fun String.parseEncryptedRoom(): EncryptedRoom {
    val parts = split(Regex("(-)|(\\[)|(\\])")).toTypedArray()
    return EncryptedRoom(
            parts.asList().subList(0, parts.size - 3),
            parts[parts.size - 3].toInt(),
            parts[parts.size - 2]
    )
}

fun main(args: Array<String>) {
    println(
            Files.lines(Paths.get(ClassLoader.getSystemResource("year2016/task04/input.txt").toURI()))
                    .map(String::trim)
                    .map(String::parseEncryptedRoom)
                    .filter(EncryptedRoom::isReal)
                    .mapToInt(EncryptedRoom::id)
                    .sum()
    )
}
