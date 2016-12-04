package year2015.task07

import org.slf4j.LoggerFactory
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.regex.Pattern
import java.util.stream.Collectors.toList

private val log = LoggerFactory.getLogger("Signals")

data class Wire(val name: String)

data class Signal(val value: Int) {
    init {
        if (value > 65535 || value < 0) {
            throw IllegalArgumentException("Wrong value $value")
        }
    }
}

infix fun Signal.and(other: Signal): Signal {
    return Signal(value and other.value)
}

infix fun Signal.or(other: Signal): Signal {
    return Signal(value or other.value)
}

infix fun Signal.shl(shift: Int): Signal {
    return Signal(value shl shift and 0xffff)
}

infix fun Signal.shr(shift: Int): Signal {
    return Signal(value shr shift)
}

fun Signal.inv(): Signal {
    return Signal(value.inv() and 0xffff)
}

sealed class SignalProvider {
    class Constant(val signal: Signal) : SignalProvider()
    class WireProvider(val wire: Wire) : SignalProvider()
    class And(val leftProvider: SignalProvider, val rightProvider: SignalProvider) : SignalProvider()
    class Or(val leftProvider: SignalProvider, val rightProvider: SignalProvider) : SignalProvider()
    class LeftShift(val provider: SignalProvider, val shift: Int) : SignalProvider()
    class RightShift(val provider: SignalProvider, val shift: Int) : SignalProvider()
    class Not(val provider: SignalProvider) : SignalProvider()
}

fun SignalProvider.str(): String {
    return when (this) {
        is SignalProvider.Constant -> signal.value.toString()
        is SignalProvider.WireProvider -> wire.name
        is SignalProvider.And -> "${leftProvider.str()} AND ${rightProvider.str()}"
        is SignalProvider.Or -> "${leftProvider.str()} OR ${rightProvider.str()}"
        is SignalProvider.LeftShift -> "${provider.str()} LSHIFT $shift"
        is SignalProvider.RightShift -> "${provider.str()} RSHIFT $shift"
        is SignalProvider.Not -> "NOT ${provider.str()}"
    }
}

class CircuitComputer(val map: Map<Wire, SignalProvider>) {
    private val cache = HashMap<Wire, Signal>()

    private fun Wire.computeExhausted(): Signal {
        return cache[this] ?: SignalProvider.WireProvider(this).computeExhausted(0)
    }

    private fun SignalProvider.computeExhausted(deep: Int): Signal {
        val repeat = "  ".repeat(deep)
        log.debug(repeat + str())
        return when (this) {
            is SignalProvider.Constant -> signal
            is SignalProvider.WireProvider -> {
                val cachedSignal = cache[wire]
                when (cachedSignal) {
                    is Signal -> {
                        log.debug(repeat + "$wire = $cachedSignal")
                        cachedSignal
                    }
                    else -> {
                        val signalProvider = map[wire] ?: throw IllegalArgumentException("No wire found $wire")
                        val signal = signalProvider.computeExhausted(deep + 1)
                        cache.put(wire, signal)
                        signal
                    }
                }
            }
            is SignalProvider.And -> leftProvider.computeExhausted(deep + 1) and rightProvider.computeExhausted(deep + 1)
            is SignalProvider.Or -> leftProvider.computeExhausted(deep + 1) or rightProvider.computeExhausted(deep + 1)
            is SignalProvider.LeftShift -> provider.computeExhausted(deep + 1) shl shift
            is SignalProvider.RightShift -> provider.computeExhausted(deep + 1) shr shift
            is SignalProvider.Not -> provider.computeExhausted(deep + 1).inv()
        }
    }

    fun compute(wire: Wire): Signal {
        return wire.computeExhausted()
    }

    fun computeAll(): Map<Wire, Signal> {
        map.keys.map { wire -> wire to wire.computeExhausted() }
                .forEach { cache.put(it.first, it.second) }
        return cache
    }
}

fun SignalProvider.compute(circuitMap: Map<Wire, SignalProvider>, computedMap: Map<Wire, Signal>): Pair<Signal, Map<Wire, Signal>> {
    log.debug(this.str())
    return when (this) {
        is SignalProvider.Constant -> signal to computedMap
        is SignalProvider.WireProvider -> (computedMap[wire]?.let { it to computedMap }) ?: (wire.compute(circuitMap, computedMap).let { it[wire]!! to it })
        is SignalProvider.And -> {
            val leftProvider = leftProvider
            val (leftSignal, leftMap) = leftProvider.compute(circuitMap, computedMap)
            val rightProvider = rightProvider
            val (rightSignal, rightMap) = rightProvider.compute(circuitMap, leftMap)
            leftSignal and rightSignal to rightMap
        }
        is SignalProvider.Or -> {
            val leftProvider = leftProvider
            val (leftSignal, leftMap) = leftProvider.compute(circuitMap, computedMap)
            val rightProvider = rightProvider
            val (rightSignal, rightMap) = rightProvider.compute(circuitMap, leftMap)
            leftSignal or rightSignal to rightMap
        }
        is SignalProvider.LeftShift -> {
            val provider = provider
            val (signal, map) = provider.compute(circuitMap, computedMap)
            signal shl shift to map
        }
        is SignalProvider.RightShift -> {
            val provider = provider
            val (signal, map) = provider.compute(circuitMap, computedMap)
            signal shr shift to map
        }
        is SignalProvider.Not -> {
            val provider = provider
            val (signal, map) = provider.compute(circuitMap, computedMap)
            signal.inv() to map
        }
    }
}

fun Wire.compute(circuitMap: Map<Wire, SignalProvider>, computedMap: Map<Wire, Signal>): Map<Wire, Signal> {
    return circuitMap[this]?.let { signalProvider ->
        log.debug("${signalProvider.str()} -> $this")
        val (signal, map) = signalProvider.compute(circuitMap, computedMap)
        log.debug("$this = $signal")
        map.plus(this to signal)
    } ?: throw IllegalArgumentException("Unknown wire $this")
}

fun computeAll(circuitMap: Map<Wire, SignalProvider>): Map<Wire, Signal> {
    return circuitMap.keys.fold(emptyMap()) { map, wire ->
        wire.compute(circuitMap, map)
    }
}

fun Wire.compute(circuitMap: Map<Wire, SignalProvider>): Signal {
    return compute(circuitMap, emptyMap())[this]!!
}

private val alphaTester = Pattern.compile("[\\p{Alpha}]+").asPredicate()

fun String.alpha() = alphaTester.test(this)

private val numericTester = Pattern.compile("[\\p{Digit}]+").asPredicate()

fun String.numeric() = numericTester.test(this)

fun String.parseDescription(): Pair<Wire, SignalProvider> {
    fun String.parseWire(): Wire {
        return Wire(this)
    }

    fun String.parseSignalProvider(): SignalProvider {
        return when {
            contains(" AND ") -> {
                val parts = split(" AND ")
                SignalProvider.And(parts[0].parseSignalProvider(), parts[1].parseSignalProvider())
            }
            contains(" OR ") -> {
                val parts = split(" OR ")
                SignalProvider.Or(parts[0].parseSignalProvider(), parts[1].parseSignalProvider())
            }
            contains(" LSHIFT ") -> {
                val parts = split(" LSHIFT ")
                SignalProvider.LeftShift(parts[0].parseSignalProvider(), parts[1].toInt())
            }
            contains(" RSHIFT ") -> {
                val parts = split(" RSHIFT ")
                SignalProvider.LeftShift(parts[0].parseSignalProvider(), parts[1].toInt())
            }
            startsWith("NOT ") -> {
                SignalProvider.Not(substring("NOT ".length).parseSignalProvider())
            }
            alpha() -> SignalProvider.WireProvider(parseWire())
            numeric() -> SignalProvider.Constant(Signal(toInt()))
            else -> throw IllegalArgumentException("Unexpected signal provider $this")
        }
    }

    val parts = split(" -> ")
    return parts[1].parseWire() to parts[0].parseSignalProvider()
}

fun main(args: Array<String>) {
    val descriptions = (Files.lines(Paths.get(ClassLoader.getSystemResource("year2015/task07/input.txt").toURI()))
            .map(String::trim)
            .map(String::parseDescription)
            .collect(toList()) as List<Pair<Wire, SignalProvider>>).toMap()
    val result = computeAll(descriptions)
    val nodeAndRelations = descriptions.toRelations()
    val dot = nodeAndRelations.toDotWithValue(result)
    val json = nodeAndRelations.toJson(result)
    val wire = Wire("a")
    println(result[wire]!!)
    println(CircuitComputer(descriptions).compute(wire))
}

