package chapter3.exercises.ex1

import chapter3.Cons
import chapter3.List
import chapter3.Nil
import chapter4.foldRight
import chapter4.sec4.isEmpty
import chapter4.sec4.size
import chapter5.sec1.map
import chapter9.solutions.final.JSONParser.sp
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.WordSpec
import kotlinx.collections.immutable.toImmutableList
import utils.SOLUTION_HERE


// tag::init[]
fun <A> tail(xs: List<A>): List<A> {
    return  when(xs){
        is Nil-> throw java.lang.IllegalStateException()
        is Cons<A>->xs.tail
    }
}


// end::init[]

//TODO: Enable tests by removing `!` prefix
class Exercise1 : WordSpec({
    "list tail" should {
        "return the the tail when present" {
            tail(List.of(1, 2, 3, 4, 5)) shouldBe
                List.of(2, 3, 4, 5)
        }

        "throw an illegal state exception when no tail is present" {
            shouldThrow<IllegalStateException> {
                tail(Nil)
            }
        }
    }
})
