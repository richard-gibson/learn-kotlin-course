package app.content.overview

import Markdown
import app.annotatedCode
import app.divider
import app.runnableCode
import markdown
import react.RBuilder
import react.dom.img

val collections: RBuilder.() -> Unit = {
    Markdown {
        attrs.source = """
                |   # Collections

                |   A collection is a class used to represent a set of similar items as a single unit. These classes are used for grouping and managing related objects.
                |
                |   Kotlin provides Collection Types and related functions in the Kotlin standard libraries kotlin.collections package.
                """.trimIndent().trimMargin()
    }
    basicCollectionTypes()
    mapFilter()
}
private val mapFilter: RBuilder.() -> Unit = {
    annotatedCode("""
        # Filter, Map, FlatMap, Fold

        The Kotlin standard library comes with many useful functions that can be found in other languages like Scala or C#.

        Filter, Map, FlatMap and Fold are all added to the collection libraries to help with many different tasks dealing with collections of things.

        Each of these functions are known as higher order functions because they take a function as an argument, See [Functions](functions).

        ## Filter

        Filter does what it says on the tin, it filters. You give it a function that tells it to remove the item (false) or keep it (true).

    """, "inline fun <T> Iterable<T>.filter(predicate: (T) -> Boolean): List<T>", readOnly = true)
    runnableCode("""
        // A Filter that keeps items greater than 5
        val filteredList = listOf(1,6,5,3,7,4,3,6,7).filter { it > 5 } // Notice the braces
        println(filteredList)
    """.trimIndent())
    runnableCode("""
        // Filter the list of strings so that only string of length smaller than 4 exist
        val originalList = listOf("a", "abc", "def", "abcdef", "defghi", "adfddds", "z")
        val filteredList: List<String> = TODO()
        println(filteredList)
    """.trimIndent(), tryCode = true)

    annotatedCode("""
        ## Map

        The map function maps one list of items onto another list of items.

        You provide a transformation function that converts a single item into something else and the result is a collection of those items.
    """, """
        inline fun <T, R> Array<out T>.map(transform: (T) -> R): List<R>
    """, readOnly = true)
    runnableCode("""
        // Map a list of Integers into a list of Strings
        val mappedList = listOf(1,6,5,3,7,4,3,6,7).map { "XX ${'$'}it XX" }
        println(mappedList)
    """.trimIndent())
    runnableCode("""
        // Create a list of Cars from a list of Car models using the map function

        data class Car(val model: String)
        val models = listOf("350z", "Micra", "Juke")
        val cars: List<Car> = TODO()
        println(cars)
    """.trimIndent(), tryCode = true)

    annotatedCode(
        """
            ## FlatMap

            FlatMap is a bit more fancy than just map. With Map you are building a new list of items returned from your transformation function.

            FlatMap does something similar except that it expects each transform to yield a list of items.

            Each list is then flattened.
        """, """
            inline fun <T, R> Array<out T>.flatMap(transform: (T) -> Iterable<R>): List<R>
        """, readOnly = true
    )
    runnableCode("""
        // Here is the same example from map above
        // This time each item will transform to a list of Strings prefixing the number with A, B and C
        val mappedList = listOf(1,6,5,3,7,4,3,6,7).map { listOf("A${'$'}it", "B${'$'}it", "C${'$'}it") }
        println(mappedList) // This is a list of lists of strings

        //Here is the same but with flat map
        val flatMappedList = listOf(1,6,5,3,7,4,3,6,7).flatMap { listOf("A${'$'}it", "B${'$'}it", "C${'$'}it") }
        println(flatMappedList) // This is a list of strings
    """.trimIndent())

    runnableCode("""
        // Create a list of Cars from a map of makes and models using flatMap

        // Hint: You will need map for this one too

        data class Car(val make: String, val model: String)

        val makeModels = mapOf(
            "Nissan" to listOf("350z", "Micra", "Juke"),
            "Ford" to listOf("Fiesta", "Mustang", "GT")
        )
        val cars: List<Car> = TODO()
        println(cars)
    """.trimIndent(), tryCode = true)

    annotatedCode(
            """
            ## Fold

            Fold takes a collection of items and rolls over them collecting state as it goes.

            How it does it and what it collects is up to you.

            Fold takes two arguments, an initial state and an operation that takes the current item and previous state and returns the next state.

        """, """
            inline fun <T, R> Array<out T>.fold(initial: R, operation: (acc: R, T) -> R): R
        """, readOnly = true
    )
    runnableCode("""
        // We can use Fold to build up a String for example

        val intList = listOf(1,6,5,3,7,4,3,6,7)

        val intString = intList.fold("") { string, number -> string + number } // The type (String) is inferred from the initial value

        println(intString)
    """.trimIndent())

    runnableCode("""
        // Use Fold to find the sum of these numbers

        val intList = listOf(1,6,5,3,7,4,3,6,7)

        val sum = TODO()

        println(sum)
    """.trimIndent(), tryCode = true)

}

private val basicCollectionTypes: RBuilder.() -> Unit = {
    markdown("""
        ## Collection Hierarchy
        Standard collections are collections from Java, which are hidden behind interfaces. Creation of them is made by standard top-level functions **listOf**, **setOf**, **mutableListOf** etc..
    """.trimIndent())
    img(src = "collection-hierarchy.png"){ attrs.width = "800px"}
    divider()
    annotatedCode(
            annotation = """
                ## Basic Collection Types

                ### Array
                A container for holding a fixed number of entries of a given type. Arrays in Kotlin are represented by the Array class. To create an array, we can use a library function **arrayOf()**.
            """,
            code = """
                val array = arrayOf("Wolverine", "Punisher", "Thor")
                array.forEach{println(it)}
            """
    )
    annotatedCode(
            annotation = """
                Kotlin also has specialized classes to represent arrays of primitive types without boxing overhead
            """,
            code = """
                val intArray: IntArray = intArrayOf(1, 2, 3)
                intArray.forEach{println(it)}
            """
    )
    annotatedCode(
            annotation = """
                ### List
                List, Set, and Map are all interfaces which are implemented by many different classes.
                An ordered collection of elements.
            """,
    code = """
               val mutable = mutableListOf("Batman", "Superman", "Wonder Woman")
               mutable.add("Aquaman")
               println(mutable)

               val immutable = listOf("Robin", "Raven", "Cyborg", "Starfire", "Beast Boy")
               // immutable.add("Storm")
               println(immutable)

               /* Uncommenting the above will cause a compiler error as
                immutable collections don't have the mutating functions */
            """
    )
    annotatedCode(
            annotation = """
                ### Set
                An unorder collection that does not support duplicate entries
            """,
            code = """
               |    val set = setOf("Spider-man", "Hawkman", "Arrow", "The Flash", "Arrow")
               |    println(set)
            """
    )
    divider()
    annotatedCode(
            annotation = """
                ## Sequence
                Sequences are a key abstraction to functional programming in Kotlin, a concept quite similar to Java 8 Streams.
                Sequences enable you to easily operate upon collections of elements by chaining pure function calls with a rich fluent API.

                Sequence type represents lazily evaluated collections.
                Top-level functions for instantiating sequences and extension functions for sequences.
            """,
            code = """
               |    val seq = sequenceOf("Penguin", "Joker", "Riddler")
               |    seq.forEach{println(it)}
            """
    )
    annotatedCode(
            annotation = """
                Sequences are lazy, so intermediate functions for Sequence processing don’t do any calculations.
                Instead they return a new Sequence that decorates the previous one with a new operation.
                All these computations are evaluated during terminal operation like toList or count.
                On the other hand, functions for Iterable processing returns a new collection.
            """,
            code = """
                |   sequenceOf(1,2,3)
                |   .filter { println("Filter ${'$'}it, "); it % 2 == 1 }
                |   .map { println("Map ${'$'}it, "); it * 2 }
                |   .toList()
                |
                |   println()
                |
                |   listOf(1,2,3)
                |   .filter { println("Filter ${'$'}it, "); it % 2 == 1 }
                |   .map { println("Map ${'$'}it, "); it * 2 }
            """
    )
    divider()
    annotatedCode(
            annotation = """
                |   ## Collection extension functions
                |
                |   The standard libraries collection & sequence package offers various extension functions to ease working with collections
                |
                |   ### Transformation functions
                |
                |   Kotlin offers extension functions to transform between different collection types.
            """,
            code = """
                |   val set = listOf("Spider-man", "Hawkman", "Arrow", "The Flash", "Arrow").toSet()
                |   println(set)
                |
                |   val setlist = setOf("Robin", "Raven", "Cyborg", "Starfire", "Beast Boy").toList()
                |   println(setlist)
                |
                |   val list = sequenceOf("Penguin", "Joker", "Riddler").toList()
                |   println(list)
                |
                |   val sequence = listOf("Wolverine", "Punisher", "Thor").asSequence()
                |   sequence.forEach {println(it)}
            """
    )
    annotatedCode(
            annotation = """
                |   ### Checks functions
                |
                |   Functions are offered to check the state of the collection.
            """,
            code = """
                |  val intList = listOf(1, 2, 3)
                |
                |  val all = intList.all { it < 4 } // All of them are less than 4
                |  println(all)
                |
                |  val any = intList.any() // Collection has elements
                |  println(any)
                |
                |  val contains = intList.contains(3) // Collection contains value of 3
                |  println(contains)
            """
    )
    annotatedCode(
            annotation = """
                |   ### Transf­ormer functions
                |
                |   Functions are offered to transf­orm the state of the collection.
            """,
    code = """
                |  val intList = listOf(1, 2, 3)
                |
                |  val mapped = intList.map { it + 1 } // Returns a new list by transforming all elements from the initial Iterable
                |  println(mapped)
                |
                |  val sliced = intList.slice(1..2) // Takes a range from collection based on indexes
                |  println(sliced)
                |
                |  val sorted = intList.sorted() // Returns a list of all elements sorted according to their natural sort order
                |  println(sorted)
            """
    )
    annotatedCode(
            annotation = """
                |   ### Aggregator functions
                |
                |   Functions are offered to support aggregation of the collection.
            """,
            code = """
                |   val intList = listOf(1, 2, 3)
                |
                |   val count = intList.count() // Returns the size of the list
                |   println(count)
                |
                |   val max = intList.max() // Maximum value in the list.
                |   println(max)
                |
                |   val min = intList.min() // Minimum value in the list.
                |   println(min)
                |
                |   /* Accumulates values starting with initial and applying operation from left to right.
                |   Lambda receives accumulated value and current value. */
                |
                |   val folded = intList.fold(10) { accumulator, value ->
                |       accumulator + value
                |   }
                |   println(folded)
                |
                |
                |   /* Accumulates values starting with first value and applying operation from left to right.
                |   Lambda receives accumulated value and current value. */
                |
                |   val reduced = intList.reduce { accumulator, value ->
                |       accumulator + value
                |   }
                |   println(reduced)
            """
    )
}