package chapter2.exercises.ex1

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import kotlinx.collections.immutable.persistentMapOf
import utils.SOLUTION_HERE

//TODO: Enable tests by removing `!` prefix
class Exercise1 : WordSpec({
    //tag::init[]
    fun fib(i: Int): Int {

        tailrec fun go(
            previousNumber: Int,
            currentNumber: Int,
            iterator: Int
        ): Int {

            val acc = previousNumber + currentNumber

            return when( iterator <= 0){
                true-> acc
                else-> go(
                    previousNumber = currentNumber,
                    currentNumber = acc,
                    iterator = iterator - 1
                )
            }

                /*
           if(iterator<=0) return acc
             return go(
              numeroAnterior = numeroActual,
              numeroActual = acc,
              iterator = iterator - 1
            )

          */
            }



        return when(i){

            0->0
            1->1
            else->go(
                previousNumber = 0,
                currentNumber = 1,
                iterator = i-2
            )
        }


        /*
        if(i==1){
            return 1
        }else{
            var acumulador = 0
            var numeroAnterior = 0
            var numeroActual = 1
            var j = 1

            while (j<i){
                acumulador = numeroAnterior + numeroActual
                numeroAnterior = numeroActual
                numeroActual = acumulador
                j++
            }

            return numeroActual
       */

        /*
        fun go(n:Int,acc:Int):Int{
            if(n<=0) acc
            else go(n-1,n*acc)
            return go(i,1)
        }

        when(i){
            0->0
            1->1
            else->go(i,0)

        }

         */


    }







    //end::init[]

    "fib" should {
        "return the nth fibonacci number" {
            persistentMapOf(
                1 to 1,
                2 to 1,
                3 to 2,
                4 to 3,
                5 to 5,
                6 to 8,
                7 to 13,
                8 to 21
            ).forEach { (n, num) ->
                fib(n) shouldBe num
            }
        }
    }
})
