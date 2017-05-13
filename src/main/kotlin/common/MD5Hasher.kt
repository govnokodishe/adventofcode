package common

import org.apache.commons.codec.binary.Hex
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.stream.LongStream
import java.util.stream.Stream

object MD5Hasher {
    val md = MessageDigest.getInstance("MD5")
    val defaultCharset: Charset = StandardCharsets.UTF_8

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

fun getFirstNHashesStartsWithZeros(key: String, n: Long, zerosCount: Int): Stream<Pair<Long,String>> {
    val zeros = "0".repeat(zerosCount)
    return LongStream.iterate(0) { it + 1 }
            .mapToObj { it to MD5Hasher.hash((key + it.toString())).toHex() }
            .filter { it.second.startsWith(zeros) }
            .limit(n)
}

fun getFirstHashStartsWithZeros(key: String, zerosCount: Int): Pair<Long,String> {
    return getFirstNHashesStartsWithZeros(key, 1, zerosCount).findAny().get()
}
