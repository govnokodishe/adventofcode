package year2016.task06

import common.count
import common.readLines
import java.nio.file.Paths

fun <K> Map<K, Int>.highestValueEntry(): Map.Entry<K, Int>? {
    return entries.sortedByDescending(Map.Entry<K, Int>::value).firstOrNull()
}

fun Sequence<String>.correctErrors(): Sequence<Char> {
    return fold(emptyArray<Map<Char, Int>>()) { maps, str ->
        val chars = str.toCharArray()
        val newMaps = when {
            chars.size > maps.size -> maps.plus((0..chars.size - maps.size).map { emptyMap<Char, Int>() })
            else -> maps
        }
        chars.forEachIndexed { i, c -> newMaps[i] = newMaps[i].count(c) }
        newMaps
    }
            .asSequence()
            .map(Map<Char, Int>::highestValueEntry)
            .filterNotNull()
            .map(Map.Entry<Char, Int>::key)
}

fun Sequence<String>.correctedString(): String {
    return correctErrors().joinToString("")
}

fun main(args: Array<String>) {
    Paths.get(ClassLoader.getSystemResource("year2016/task06/input.txt").toURI()).readLines { lines ->
        println(lines.correctedString())
    }
}
