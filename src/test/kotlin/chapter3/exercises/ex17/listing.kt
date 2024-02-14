package chapter3.exercises.ex17

import chapter3.Cons
import chapter3.List
import chapter3.foldRight
import chapter5.sec1.x
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import utils.SOLUTION_HERE

// tag::init[]
fun <A, B> map(xs: List<A>, f: (A) -> B): List<B> {
    return foldRight(xs,z= List.of(), f ={element,acc->Cons(head = f(element), tail = acc)})
}

//TODO: Enable tests by removing `!` prefix
class Exercise17 : WordSpec({
    "list map" should {
        "apply a function to every list element" {
            map(List.of(1, 2, 3, 4, 5)) { it * 10 } shouldBe
                List.of(10, 20, 30, 40, 50)
        }
    }
})
