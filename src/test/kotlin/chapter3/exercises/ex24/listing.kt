package chapter3.exercises.ex24

import chapter11.sec4.g
import chapter3.Branch
import chapter3.Leaf
import chapter3.Tree
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import utils.SOLUTION_HERE

// tag::init[]
fun <A> size(tree: Tree<A>): Int {

    tailrec fun go (tree: Tree<A>,iterator: Int):Int{

    return    when(tree){
            is Branch->go(tree.left,iterator+2)
             is Leaf->iterator+2
        }

    }

    return go(tree,1)


}
// end::init[]

//TODO: Enable tests by removing `!` prefix
class Exercise24 : WordSpec({

    "tree size" should {
        "determine the total size of a tree" {
            val tree =
                Branch(
                    Branch(Leaf(1), Leaf(2)),
                    Branch(Leaf(3), Leaf(4))
                )
            size(tree) shouldBe 7
        }


    }

})
