package year2015.task07

import year2015.task07.Node.WireNode

fun Node.id(map: Map<Wire, Signal>): String {
    return when (this) {
        is Node.SimpleNode -> name
        is WireNode -> "${name}_${map[Wire(name)]!!.value}"
    }
}

fun Node.toJson(map: Map<Wire, Signal>): String {
    return "{\"name\":\"${id(map)}\"}"
}

fun Relation.toJson(map: Map<Wire,Signal>): String {
    return "{\"src\":\"${src.id(map)}\",\"dest\":\"${dest.id(map)}\"}"
}

fun Set<Node>.nodesToJson(map: Map<Wire, Signal>): String {
    return "\"nodes\":${map { it.toJson(map) }.joinToString(",","[","]")}"
}

fun Set<Relation>.realtionsToJson(map: Map<Wire, Signal>): String {
    return "\"edges\":${map { it.toJson(map) }.joinToString(",","[","]")}"
}

fun Set<Relation>.toJson(map: Map<Wire, Signal>): String {
    val nodes = flatMap { listOf(it.src, it.dest) }.toSet()
    return "{${nodes.nodesToJson(map)},${realtionsToJson(map)}}"
}
