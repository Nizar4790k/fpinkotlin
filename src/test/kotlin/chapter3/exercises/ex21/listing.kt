package chapter3.exercises.ex21

import chapter3.Cons
import chapter3.List
import chapter3.Nil
import chapter3.exercises.ex8.length
import chapter3.flatMap
import chapter3.foldRight
import chapter3.reverse
import chapter5.sec1.x
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import utils.SOLUTION_HERE

// tag::init[]
fun add(xa: List<Int>, xb: List<Int>): List<Int> {
    return when{
        xa is Cons && xb is Cons->Cons(head = xa.head + xb.head, tail = add(xa.tail,xb.tail))
        else->Nil
    }
}
// end::init[]

//TODO: Enable tests by removing `!` prefix
class Exercise21 : WordSpec({
    "list add" should {
        "add elements of two corresponding lists" {
            add(List.of(1, 2, 3), List.of(4, 5, 6)) shouldBe
                List.of(5, 7, 9)
        }
    }
})
