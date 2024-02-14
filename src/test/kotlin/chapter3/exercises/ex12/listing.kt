package chapter3.exercises.ex12


import chapter3.List
import chapter3.exercises.ex10.sumL
import chapter3.foldLeft
import chapter3.foldRight
import chapter5.sec1.x
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import utils.SOLUTION_HERE

// tag::init[]
fun <A, B> foldLeftR(xs: List<A>, z: B, f: (B, A) -> B): B {

    return foldLeft(xs,foldRight(xs,z) { _, y -> y },f)
}
fun <A, B> foldRightL(xs: List<A>, z: B, f: (A, B) -> B): B {


    return foldRight(xs, foldLeft(xs,z) { x, _ -> x },f)
}

// end::init[]

//TODO: Enable tests by removing `!` prefix
class Exercise12 : WordSpec({
    "list foldLeftR" should {
        "implement foldLeft functionality using foldRight" {
            foldLeftR(
                List.of(1, 2, 3, 4, 5),
                0,
                { x, y -> x + y }) shouldBe 15
        }
    }

    "list foldRightL" should {
        "implement foldRight functionality using foldLeft" {
            foldRightL(
                List.of(1, 2, 3, 4, 5),
                0,
                { x, y -> x + y }) shouldBe 15
        }
    }
})
