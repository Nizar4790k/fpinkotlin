package chapter3.exercises.ex15

import chapter3.Cons
import chapter3.List
import chapter3.foldRight
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import utils.SOLUTION_HERE

// tag::init[]
fun increment(xs: List<Int>): List<Int> {
    return foldRight(xs,List.of(), f = {element:Int,list:List<Int>->Cons(head = element+1, tail = list)})
}
// end::init[]

//TODO: Enable tests by removing `!` prefix
class Exercise15 : WordSpec({
    "list increment" should {
        "add 1 to every element" {
            increment(List.of(1, 2, 3, 4, 5)) shouldBe
                List.of(2, 3, 4, 5, 6)
        }
    }
})
