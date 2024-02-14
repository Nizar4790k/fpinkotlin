package chapter3.exercises.ex23

import chapter3.Cons
import chapter3.List
import chapter3.exercises.ex3.drop

import chapter3.reverse
import chapter3.solutions.sol8.length
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

// tag::startsWith[]
fun <A> startsWith(l1: List<A>, l2: List<A>): Boolean {

    val sizeL1 = length(l1)
    val sizeL2 = length(l2)
    if(sizeL1<sizeL2) return false

    return when(l1==l2){
       true->true
       else->{
           when(sizeL2==1 && (l1 is Cons && l2 is Cons)){
               true->l1.head==l2.head
               false ->{
                   val subList = reverse(drop(l=reverse(l1), n = sizeL1-sizeL2))
                   subList==l2
               }
           }
       }
   }
}


// end::startsWith[]}

// tag::init[]
tailrec fun <A> hasSubsequence(xs: List<A>, sub: List<A>): Boolean {

    val startsWith1 = startsWith(l1 = xs, l2 = sub)
    return when(startsWith1){
        true->true
        false->{
            when(xs is Cons && sub is Cons){
                true-> hasSubsequence(xs = xs.tail, sub = sub)
                false->false
            }
        }
    }
}

//TODO: Enable tests by removing `!` prefix
class Exercise23 : WordSpec({
    "list subsequence" should {
        "determine if a list starts with" {
            val xs = List.of(1, 2, 3)
            startsWith(xs, List.of(1)) shouldBe true
            startsWith(xs, List.of(1, 2)) shouldBe true
            startsWith(xs, xs) shouldBe true
            startsWith(xs, List.of(2, 3)) shouldBe false
            startsWith(xs, List.of(3)) shouldBe false
            startsWith(xs, List.of(6)) shouldBe false
        }

        "identify subsequences of a list" {
            val xs = List.of(1, 2, 3, 4, 5)
            hasSubsequence(xs, List.of(1)) shouldBe true
            hasSubsequence(xs, List.of(1, 2)) shouldBe true
            hasSubsequence(xs, List.of(2, 3)) shouldBe true
            hasSubsequence(xs, List.of(3, 4)) shouldBe true
            hasSubsequence(xs, List.of(3, 4, 5)) shouldBe true
            hasSubsequence(xs, List.of(4)) shouldBe true
            hasSubsequence(xs, List.of(1, 4)) shouldBe false
            hasSubsequence(xs, List.of(1, 3)) shouldBe false
            hasSubsequence(xs, List.of(2, 4)) shouldBe false
        }
    }
})
