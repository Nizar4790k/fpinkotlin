package chapter4.exercises.ex4

import chapter3.List
import chapter3.exercises.ex18.filter
import chapter4.None
import chapter4.Option
import chapter4.Some
import chapter4.map

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec


//tag::init[]
 fun <A> sequence(xs: List<Option<A>>): Option<List<A>> {

    fun go(xs: List<Option<A>>): List<A> {

        val filtered =  filter(xs){it==None}

        return when (filtered == List.empty<Option<A>>()) {
            true -> xs.map {
                val result = it as Some<A>
                result.get
            }
            false -> List.of()
        }
    }

    val result = go(xs)

    return when (result == List.empty<A>()) {
        true -> None
        false -> Some(result)
    }
}
//end::init[]

//TODO: Enable tests by removing `!` prefix
class Exercise4 : WordSpec({

    "sequence" should {
        "turn a list of some options into an option of list" {
            val lo =
                List.of(Some(10), Some(20), Some(30))
            sequence(lo) shouldBe Some(List.of(10, 20, 30))
        }

    }
})
