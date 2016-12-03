package year2015.task04

import org.apache.commons.codec.binary.Hex
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets.UTF_8
import java.security.MessageDigest
import java.util.stream.LongStream

object MD5Hasher {
    val md = MessageDigest.getInstance("MD5")
    val defaultCharset: Charset = UTF_8

    fun hash(str: String): ByteArray {
        return hash(str, defaultCharset)
    }

    fun hash(str: String, charset: Charset): ByteArray {
        return md.digest(str.toByteArray(charset))
    }
}

fun ByteArray.toHex(): String {
    return Hex.encodeHexString(this)
}

fun getAnswerForKey(key: String): Long {
    return LongStream.iterate(0) { it + 1 }
            .mapToObj { it to MD5Hasher.hash((key + it.toString())) }
            .filter { it.second.toHex().startsWith("00000") }
            .map { it.first }
            .findFirst().get()
}

fun main(args: Array<String>) {
    println(getAnswerForKey("iwrupvqb"))
}
