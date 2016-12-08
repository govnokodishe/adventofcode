package year2016.task07

import common.readLines
import one.util.streamex.StreamEx
import java.nio.file.Paths
import java.util.regex.Pattern

data class IpAddress(val head: IpAddressNode.Ordinal)

private val partTester = Pattern.compile("(\\p{Alpha})+").asPredicate()

sealed class IpAddressNode(val part: String, val next: IpAddressNode?) {
    class Ordinal(part: String, next: Hypernet?) : IpAddressNode(part, next) {
        constructor(part: String) : this(part, null)
    }
    class Hypernet(part: String, next: Ordinal) : IpAddressNode(part, next)

    init {
        if (!partTester.test(part)) {
            throw IllegalArgumentException("Wrong part for ip address node")
        }
    }
}

fun String.containsAbba(): Boolean {
    return when {
        length < 4 -> false
        get(0) != get(1) && substring(0..1) == substring(2..3).reversed() -> true
        else -> substring(1).containsAbba()
    }
}

fun IpAddressNode.supportsTls(): Boolean {
    return supportsTls(false)
}

fun IpAddressNode.supportsTls(ordinalWithAbbaFound: Boolean): Boolean {
    return when (part.containsAbba()) {
        true -> when (this) {
            is IpAddressNode.Ordinal -> next?.supportsTls(true) ?: true
            is IpAddressNode.Hypernet -> false
        }
        false -> next?.supportsTls(ordinalWithAbbaFound) ?: ordinalWithAbbaFound
    }
}

fun IpAddress.supportsTls(): Boolean {
    return head.supportsTls()
}

private sealed class MutableIpAddressNode(val part: String, var next: MutableIpAddressNode?) {
    class Ordinal(part: String, next: Hypernet?) : MutableIpAddressNode(part, next) {
        override fun toImmutable() = IpAddressNode.Ordinal(part, next?.toImmutable() as IpAddressNode.Hypernet?)
    }

    class Hypernet(part: String, next: Ordinal?) : MutableIpAddressNode(part, next) {
        override fun toImmutable() = IpAddressNode.Hypernet(part, next?.toImmutable() as IpAddressNode.Ordinal)
    }

    init {
        if (!partTester.test(part)) {
            throw IllegalArgumentException("Wrong part for ip address node")
        }
    }

    abstract fun toImmutable(): IpAddressNode
}

fun String.parseIpAddress(): IpAddress {
    val list = split("[", "]")
    val head = list.first()
    val mutableNode = MutableIpAddressNode.Ordinal(head, null)
    StreamEx.of(list).skip(1).foldLeft(mutableNode as MutableIpAddressNode) { node, str ->
        when (node) {
            is MutableIpAddressNode.Ordinal -> MutableIpAddressNode.Hypernet(str, null)
            is MutableIpAddressNode.Hypernet -> MutableIpAddressNode.Ordinal(str, null)
        }.apply {
            node.next = this
        }
    }
    return IpAddress(mutableNode.toImmutable())
}

fun main(args: Array<String>) {
    Paths.get(ClassLoader.getSystemResource("year2016/task07/input.txt").toURI()).readLines { lines ->
        println(
                lines.map(String::parseIpAddress)
                        .filter(IpAddress::supportsTls)
                        .count()
        )
    }
}