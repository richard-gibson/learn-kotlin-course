package app.content.overview

import Markdown
import app.annotatedCode
import app.divider
import app.runnableCode
import markdown
import react.RBuilder
import react.dom.code

val types: RBuilder.() -> Unit = {
    markdown("# Classes and Types")
    classes()
    divider()
    inheritance()
    divider()
    interfaces()
    divider()
    multipleInheritance()
    divider()
    typeTree()
    divider()
    unit()
    divider()
    nothing()
    divider()
    sealedClasses()
    divider()
    objectsAndCompanions()
    divider()
    typeAliases()
}

val classes: RBuilder.() -> Unit = {
    markdown("## Classes")
    annotatedCode(
            annotation = """Like Java Classes, Kotlin classes are declared using keyword class.
      | Implementation is optional and carried out inside braces
    """.trimMargin(),
            code = """class Foo {
      | fun baz(): String = "baz"
      | }
      |
      | class Bar
      | val foo = Foo()
      | println(foo.baz())
      | val bar = Bar()
      | println(bar)
      |
    """.trimMargin())
    markdown("### Constructor and init")
    annotatedCode(
            annotation = """An init block is called on initialization of a class
      |
    """.trimMargin(),
            code = """
      | class Car(colour: String, make: String, model: String, yearOfReg: Int) {
      | val modelAndMake ="${'$'}make - ${'$'}model"
      | val age: Int // must be initialised here or in init block
      |
      | init {
      |   age = 2018 - yearOfReg
      | }
      | }
      |
      | val fiesta = Car("Red", "Ford", "Fiesta", 1990)
      | println(fiesta.age)
      | println(fiesta.modelAndMake)
      |
    """.trimIndent())
    markdown("### Primary Constructors")
    annotatedCode(
            annotation = """Initialization can also be carried out using the primary constructor,
      | properties must be prefixed by val/var
    """.trimMargin(),
            code = """
      | class Car(val colour: String, val make: String, val model: String, val yearOfReg: Int) {
      | val modelAndMake ="${'$'}make - ${'$'}model"
      | }
      |
      | val fiesta = Car("Red", "Ford", "Fiesta", 1990)
      | println(fiesta.make)
      | println(fiesta.modelAndMake)
      |
    """.trimIndent())

    markdown("### Overloading Constructors")
    annotatedCode(
            annotation = """The constructor can be overloaded using the constructor keyword
    """.trimMargin(),
            code = """
      | class Car(val colour: String, val modelAndMake: String, val yearOfReg: Int) {
      | constructor(colour: String, make: String, model: String, yearOfReg: Int):
      |   this(colour, "${'$'}make - ${'$'}model",  yearOfReg)
      | }
      |
      | val fiesta = Car("Red", "Ford", "Fiesta", 1990)
      | println(fiesta.modelAndMake)
      | val escort = Car("Blue", "Ford Escort", 1995)
      | println(escort.modelAndMake)
    """.trimIndent())

}
val inheritance: RBuilder.() -> Unit = {
    markdown("## Inheritance")
    markdown("""Kotlin makes classes final by default to avoid ad hoc and messy hierarchies
  """.trimIndent())
    markdown("""The open keyword can be used to allow a class and their methods to be extended""")
    code {
        attrs["lines"] = "true"
        attrs["auto-indent"] = "true"
        attrs["highlight-on-fly"] = "true"
        +"""
      | open class Foo(val i: Int) {
      |   open fun baz(): String = "foo baz"
      | }
      | class Bar(j: Int): Foo(j) {
      |   override fun baz(): String = "bar bar"
      |  }
      | fun main() {
      |   val bar = Bar(5)
      |   val foo = Foo(3)
      |
      |   println(bar.baz())
      |   println(bar.i)
      |   println(foo.baz())
      |   println(foo.i)
      | }
      |
      """.trimMargin()
    }
}

val typeTree: RBuilder.() -> Unit = {
    markdown("""
        # Kotlin Type Tree

        The kotlin type tree can be boiled down to four distinct groups at its most simple.

        Here is a very basic depiction of the tree.

        ![Types](typeTree.png)

        > Basically put, Nothing is Everything and Everything is Anything
    """.trimIndent())
}

val unit: RBuilder.() -> Unit = {
    markdown("""
        # Unit

        In Java you had void, which was not really a type and was special.

        In Kotlin we have a type in its place called Unit.

        > Unit is an Any like all other things so it can be used like any other type

    """.trimIndent())
    runnableCode("""
        fun doNothing(): Unit {} // Unit is implied anyway
        println(doNothing()) // This function doesnt return anything but its return type is still a type (Unit)
    """.trimIndent())
}

val nothing: RBuilder.() -> Unit = {
    markdown("""
        # Nothing

        Kotlin has a very special type called Nothing.

        Weirdly nothing is actually the subtype of all other things including Unit.

        This means that no matter the return type of a function you can still return a Nothing.

        The only difficulty is you cannot make a raw Nothing instance.

        One example of the use of Nothing is the TODO() function used all over this course.

        > Nothing instances are created when an exception is thrown

    """.trimIndent())

    runnableCode("""
        fun blowUp(): String = TODO() // This is a Nothing (Which is a String and all other types too)
        println(blowUp())
    """.trimIndent())
}

val interfaces: RBuilder.() -> Unit = {
    markdown("## Interfaces")
    markdown("""Interfaces follow a similar convention to Java interfaces post version 8,
    this means default methods (fun) can have an implementation
    note: vals can not be implemented in an interface """)
    interfaceCodeBlock1()
    markdown("Unlike Java, implementing an interface uses the same notation as extending " +
            "an abstract class")
}
val interfaceCodeBlock1: RBuilder.() -> Unit = {
    code {
        attrs["lines"] = "true"
        attrs["auto-indent"] = "true"
        attrs["highlight-on-fly"] = "true"
        +"""
      | interface Foo {
      | fun baz(): String = "baz"
      | }
      |
      | interface Bar {
      |   val bop: Int
      |   fun baz(): String
      | }
      | class FooImpl: Foo
      | class BarImpl: Bar {
      |   override val bop:Int = 1
      |   override fun baz(): String = "barImpl baz"
      | }
      |
      | fun main() {
      | val foo = FooImpl()
      | val bar = BarImpl()
      |
      | println(foo.baz())
      | println(bar.baz())
      | }
      """.trimMargin()
    }
}
val multipleInheritance: RBuilder.() -> Unit = {
    markdown("### Multiple inheritance ")
    markdown("""Kotlin allows inheritance from multiple via interfaces and open/abstract classes,
    | in the case of a clash when multiple parent classes have the same fun defined, the Kotlin compiler will
    | defer implementation to the concrete class
  """.trimMargin())
    multipleInheritanceCodeBlock()
}
val multipleInheritanceCodeBlock: RBuilder.() -> Unit = {
    code {
        attrs["lines"] = "true"
        attrs["auto-indent"] = "true"
        attrs["highlight-on-fly"] = "true"
        +"""
      | interface Foo {
      | fun foo(): String = "foo"
      | fun baz(): String = "baz"
      | }
      |
      | interface Bar {
      |   fun bar(): String = "bar"
      |   fun baz(): String
      | }
      | class BazImpl: Bar, Foo {
      |  // override fun baz(): String = "barImpl baz"
      | }
      |
      | fun main() {
      | val baz = BazImpl()
      | println(baz.foo())
      | println(baz.bar())
      | println(baz.baz())
      | }
      """.trimMargin()
        //"fun main(){\n//sampleStart\n //sampleEnd\n}"
    }
}

val objectsAndCompanions: RBuilder.() -> Unit = {
    annotatedCode("""
        # Objects and Companions

        Kotlin does not have the static keyword like Java.

        A static class in Java is one that is initialized with the JVM at startup and holds its state for the lifetime of the JVM.

        This is exactly what Objects are in Kotlin. They are called Singletons.

        > Objects are mostly like classes and can extend interfaces and other classes

    """, """
        // A normal Class
        class Car
        // An Object
        object Car

        // Both can be defined together using the companion keyword
        class Car {
            companion object {
                // Companion objects can have optional bodies
            }
        }
    """, readOnly = true)

    runnableCode("""
        // Create a companion object with a function called shinyCar that creates a new shiny Car

        data class Car(val isShiny: Boolean = false) {

        }

        fun main(){
            println(Car.shinyCar())
        }

    """.trimIndent(), inMain = false, tryCode = true)
}

val typeAliases: RBuilder.() -> Unit = {
    markdown("""
        # TypeAliases

        The typealias keyword allows you to set an alias for any type in Kotlin.

        This is typically used to hide a complex type or rename types that have conflicting names.

        A good use case for them appears when requiring function types.

        > The typealias keyword can only appear at the top of a kotlin file underneath imports
    """.trimIndent())

    runnableCode("""
        typealias MyLambda = () -> String

        fun doSomething(withThis: MyLambda) = println(withThis())

        fun main(){
            doSomething {
                "Hi"
            }
        }
    """.trimIndent(), inMain = false)
}

val sealedClasses: RBuilder.() -> Unit = {
    markdown("""
        # Sealed Classes

        A sealed class is simply a class that can only be extended by classes defined within it.

        > Sealed classes are also known as algebraic data types

    """.trimIndent())

    runnableCode("""
        sealed class Event {
            data class NormalEvent(val payload: String): Event()
            data class ErrorEvent(val error: Exception): Event()
        }

        // The compiler now knows that an Event can only ever be either a NormalEvent or an ErrorEvent
        // This makes the when expression work nicely with the sealed type
        fun main(){
            val events = listOf(Event.NormalEvent("EVENT!"), Event.ErrorEvent(IllegalArgumentException("BadEvent")))
            events.forEachIndexed { index, it ->
                print("${'$'}index. ")
                when(it){
                    is Event.NormalEvent -> println("Good Event with payload: ${'$'}{it.payload}")
                    is Event.ErrorEvent -> println("Bad Event")
                }
            }
        }
    """.trimIndent(), inMain = false)
}
/**
# Classes and Types
...
## Classes
...
## Data Classes
...
## Interfaces
...
# Inheritance
...
## Kotlin Type Tree
...
## Nothing
... Talk about Nothing extending Everything including Unit
## Generics
...
### Reified
...
## Enum Classes
...
## Sealed Classes
 **/