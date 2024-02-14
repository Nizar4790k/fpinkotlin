package chapter3.exercises.ex3

import chapter3.Cons
import chapter3.List
import chapter3.Nil
import chapter3.foldLeft

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.WordSpec
import utils.SOLUTION_HERE

// tag::init[]
fun <A> drop(l: List<A>, n: Int): List<A> {

    tailrec fun go(n: Int, iterator: Int, list: List<A>): List<A> {

        return when {
            iterator < n && list is Cons<A> -> go(n, iterator+1, list.tail)
            list is Cons<A>-> list
            else ->list
        }
    }

    fun size(l: List<A>): Int {
        return l.foldLeft(l,0) { counter: Int, head: A -> counter + 1 }
    }


    return when {
        n == 0 -> l
        n > size(l)-> throw java.lang.IllegalStateException("The argument is bigger than the list")
        else -> go(n, 0, list = l)

    }
}

// end::init[]

//TODO: Enable tests by removing `!` prefix
class Exercise3 : WordSpec({
    "list drop" should {
        "drop a given number of elements within capacity" {
            drop(List.of(1, 2, 3, 4, 5), 3) shouldBe
                List.of(4, 5)
        }

        "drop a given number of elements within capacity reversed" {
            drop(List.of(5,4,3,2,1), 3) shouldBe
                List.of(2, 1)
        }

        "drop a given number of elements up to capacity" {
            drop(List.of(1, 2, 3, 4, 5), 5) shouldBe Nil
        }

        """throw an illegal state exception when dropped elements
            exceed capacity""" {
            shouldThrow<IllegalStateException> {
                drop(List.of(1, 2, 3, 4, 5), 6)
            }
        }
    }
})
