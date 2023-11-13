package chapter2.exercises.ex2

import chapter3.exercises.ex1.tail
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import kotlinx.collections.immutable.persistentListOf
import utils.SOLUTION_HERE
import java.lang.StringBuilder
import java.util.Arrays

// tag::init[]
val <T> List<T>.tail: List<T>
    get() = drop(1)

val <T> List<T>.head: T
    get() = first()

fun <A> isSorted(aa: List<A>, order: (A, A) -> Boolean): Boolean {



    if(aa.isEmpty()){
        return true
    }else{


        val max = aa.maxBy {it.toString()}
        val min = aa.maxBy {it.toString()}

        val ascMode =  order(min as A,max as A)


        val  values = ArrayList<Boolean>()

        aa.forEach{i->

            values.addAll( aa.map { j->
                order(i,j)
            })


        }

        if(ascMode){


            return values.filter { false }.isEmpty()


        }else{
            return values.filter { true }.isEmpty()
        }

    }












}


// end::init[]

//TODO: Enable tests by removing `!` prefix
class Exercise2 : WordSpec({

    "isSorted" should {
        """detect ordering of a list of correctly ordered Ints based
            on an ordering HOF""" {
            isSorted(
                persistentListOf(1, 2, 3)
            ) { a, b -> b > a || b==a} shouldBe true
        }
        """detect ordering of a list of incorrectly ordered Ints
            based on an ordering HOF""" {
            isSorted(
                persistentListOf(1, 3, 2)
            ) { a, b -> b > a  } shouldBe false
        }
        """verify ordering of a list of correctly ordered Strings
            based on an ordering HOF""" {
            isSorted(
                persistentListOf("a", "b", "c")
            ) { a, b -> b > a || b==a } shouldBe true
        }
        """verify ordering of a list of incorrectly ordered Strings
            based on an ordering HOF""" {
            isSorted(
                persistentListOf("a", "z", "w")
            ) { a, b -> b > a } shouldBe false
        }
        "return true for an empty list" {
            isSorted(persistentListOf<Int>()) { a, b ->
                b > a
            } shouldBe true
        }
    }
})
