package year2015.task07

import year2015.task07.Node.SimpleNode
import year2015.task07.Node.WireNode

fun Node.toDotWithValue(map: Map<Wire, Signal>): String {
    return when (this) {
        is SimpleNode -> name
        is WireNode -> "${name}_${map[Wire(name)]!!.value}"
    }
}

fun Relation.toDotWithValue(map: Map<Wire, Signal>): String {
    return "\"${src.toDotWithValue(map)}\" -> \"${dest.toDotWithValue(map)}\";"
}

fun Relation.toDot(): String {
    return "\"${src.name}\" -> \"${dest.name}\";"
}

fun Set<Relation>.toDot(): String {
    return map(Relation::toDot).joinToString("\n", "digraph {", "}")
}

fun Set<Relation>.toDotWithValue(map: Map<Wire, Signal>): String {
    return map { r -> "\t${r.toDotWithValue(map)}" }.joinToString("\n", "digraph {\n", "\n}")
}
