package chapter3.exercises.ex11

import chapter3.Cons
import chapter3.List
import chapter3.List.Companion.empty
import chapter3.Nil
import chapter3.exercises.ex10.lengthL
import chapter3.exercises.ex3.drop
import chapter3.exercises.ex7.z
import chapter3.foldLeft
import chapter3.foldRight
import chapter5.sec1.x
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import utils.SOLUTION_HERE
import java.lang.IllegalStateException

// tag::init[]
fun <A> reverse(xs: List<A>): List<A> {

    return foldLeft(xs, empty()) {a:List<A>,b:A->Cons(head = b, tail = a)}
}


// end::init[]

//TODO: Enable tests by removing `!` prefix
class Exercise11 : WordSpec({
    "list reverse" should {
        "reverse list elements" {
            reverse(List.of(1, 2, 3, 4, 5)) shouldBe
                List.of(5, 4, 3, 2, 1)
        }
    }
})
