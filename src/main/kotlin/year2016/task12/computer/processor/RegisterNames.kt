package year2016.task12.computer.processor

enum class RegisterNames : RegisterName {
    A {
        override fun asString(): String = "a"
    },
    B {
        override fun asString(): String = "b"
    },
    C {
        override fun asString(): String = "c"
    },
    D {
        override fun asString(): String = "d"
    }
}
