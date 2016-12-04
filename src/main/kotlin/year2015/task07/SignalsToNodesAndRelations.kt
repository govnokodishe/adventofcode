package year2015.task07

import year2015.task07.Node.SimpleNode
import year2015.task07.Node.WireNode

sealed class Node(val name: String) {
    class SimpleNode(name: String) : Node(name)
    class WireNode(name: String) : Node(name)
}

data class Relation(val src: Node,
                    val dest: Node)

infix fun Pair<Set<Node>, Set<Relation>>.merge(other: Pair<Set<Node>, Set<Relation>>): Pair<Set<Node>, Set<Relation>> {
    return first.plus(other.first) to second.plus(other.second)
}

fun SignalProvider.simpleToNodeOfException(wire: Wire): Node {
    return when (this) {
        is SignalProvider.Constant -> SimpleNode("${signal.value}_wire")
        is SignalProvider.WireProvider -> WireNode(this.wire.name)
        else -> throw IllegalArgumentException()
    }
}

fun Wire.toNode(): Node {
    return WireNode(name)
}

fun toRelations(signalProvider: SignalProvider, wire: Wire): Set<Relation> {
    val outputWireNode = wire.toNode()
    return when (signalProvider) {
        is SignalProvider.Constant -> {
            val constNode = SimpleNode(signalProvider.signal.value.toString())
            setOf(Relation(constNode, outputWireNode))
        }
        is SignalProvider.WireProvider -> {
            val wireNode = signalProvider.wire.toNode()
            setOf(Relation(wireNode, outputWireNode))
        }
        is SignalProvider.And -> {
            val andNode = SimpleNode("AND_${outputWireNode.name}")
            val leftNode = signalProvider.leftProvider.simpleToNodeOfException(wire).let {
                when (it) {
                    is WireNode -> it
                    else -> SimpleNode("${it.name}_${andNode.name}")
                }
            }
            val rightNode = signalProvider.rightProvider.simpleToNodeOfException(wire).let {
                when (it) {
                    is WireNode -> it
                    else -> SimpleNode("${it.name}_${andNode.name}")
                }
            }
            setOf(Relation(leftNode, andNode), Relation(rightNode, andNode), Relation(andNode, outputWireNode))
        }
        is SignalProvider.Or -> {
            val orNode = SimpleNode("OR_${outputWireNode.name}")
            val leftNode = signalProvider.leftProvider.simpleToNodeOfException(wire).let {
                when (it) {
                    is WireNode -> it
                    else -> SimpleNode("${it.name}_${orNode.name}")
                }
            }
            val rightNode = signalProvider.rightProvider.simpleToNodeOfException(wire).let {
                when (it) {
                    is WireNode -> it
                    else -> SimpleNode("${it.name}_${orNode.name}")
                }
            }
            setOf(Relation(leftNode, orNode), Relation(rightNode, orNode), Relation(orNode, outputWireNode))
        }
        is SignalProvider.LeftShift -> {
            val providerNode = signalProvider.provider.simpleToNodeOfException(wire)
            val shiftNode = SimpleNode("LSHIFT_${outputWireNode.name}")
            val shiftValueNode = SimpleNode("${signalProvider.shift}_${shiftNode.name}")
            setOf(Relation(providerNode, shiftNode), Relation(shiftValueNode, shiftNode), Relation(shiftNode, outputWireNode))
        }
        is SignalProvider.RightShift -> {
            val providerNode = signalProvider.provider.simpleToNodeOfException(wire)
            val shiftNode = SimpleNode("RSHIFT_${outputWireNode.name}")
            val shiftValueNode = SimpleNode("${signalProvider.shift}_${shiftNode.name}")
            setOf(Relation(providerNode, shiftNode), Relation(shiftValueNode, shiftNode), Relation(shiftNode, outputWireNode))
        }
        is SignalProvider.Not -> {
            val providerNode = signalProvider.provider.simpleToNodeOfException(wire)
            val notNode = SimpleNode("NOT_${outputWireNode.name}")
            setOf(Relation(providerNode, notNode), Relation(notNode, outputWireNode))
        }
    }
}

fun Map<Wire, SignalProvider>.toRelations(): Set<Relation> {
    return entries.map { e -> toRelations(e.value, e.key) }
            .reduce(Set<Relation>::plus)
}
