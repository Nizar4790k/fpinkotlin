package chapter3.exercises.ex28

import chapter3.Branch
import chapter3.Leaf
import chapter3.Tree
import chapter3.solutions.sol27.map
import chapter5.sec1.x
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import utils.SOLUTION_HERE

// tag::init[]
fun <A, B> fold(ta: Tree<A>, l: (A) -> B, b: (B, B) -> B): B {

    return when (ta) {
        is Leaf -> l(ta.value)
        is Branch->{
            val left = fold(ta.left,l=l, b = b)
            val rigth = fold(ta.right,l=l, b = b)
            b(left,rigth)
        }
    }

}



fun <A> sizeF(ta: Tree<A>): Int {

    return fold(ta,l ={1}, b = {x, y -> x+y+1})
}

fun maximumF(ta: Tree<Int>): Int {
    return fold(ta,{a->a},{x,y->maxOf(x,y) })

}

fun <A> depthF(ta: Tree<A>): Int{
    return fold(ta,{0},{x,y->1+ maxOf(x,y) })
}

fun <A, B> mapF(ta: Tree<A>, f: (A) -> B): Tree<B> {
    return fold(ta,{a:A->Leaf(f(a))},{b1:Tree<B>,b2:Tree<B>->Branch(b1,b2)})
}


//TODO: Enable tests by removing `!` prefix
class Exercise28 : WordSpec({
    "tree fold" should {

        val tree = Branch(
            Branch(Leaf(1), Leaf(2)),
            Branch(
                Leaf(3),
                Branch(
                    Branch(Leaf(4), Leaf(5)),
                    Branch(
                        Leaf(21),
                        Branch(Leaf(7), Leaf(8))
                    )
                )
            )
        )
        "generalise size" {
            sizeF(tree) shouldBe 15
        }

        "generalise maximum" {
            maximumF(tree) shouldBe 21
        }

        "generalise depth" {
            depthF(tree) shouldBe 5
        }

        "generalise map" {
            mapF(tree) { it * 10 } shouldBe
                Branch(
                    Branch(Leaf(10), Leaf(20)),
                    Branch(
                        Leaf(30),
                        Branch(
                            Branch(Leaf(40), Leaf(50)),
                            Branch(
                                Leaf(210),
                                Branch(Leaf(70), Leaf(80))
                            )
                        )
                    )
                )
        }
    }
})
