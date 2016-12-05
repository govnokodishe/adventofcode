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

fun getFirstNHashesStartsWithFiveZeros(key: String, n: Long): Stream<Pair<Long,String>> {
    return LongStream.iterate(0) { it + 1 }
            .mapToObj { it to MD5Hasher.hash((key + it.toString())).toHex() }
            .filter { it.second.startsWith("00000") }
            .limit(n)
}

fun getFirstHashStartsWithFiveZeros(key: String): Pair<Long,String> {
    return getFirstNHashesStartsWithFiveZeros(key, 1).findAny().get()
}
