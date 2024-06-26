package chapter4.exercises.ex2

import chapter3.List
import chapter4.isEmpty
import chapter4.size
import chapter4.sum
import chapter4.None
import chapter4.Option
import chapter4.Some
import chapter4.exercises.ex1.flatMap
import chapter4.getOrElse
import chapter4.map
import io.kotlintest.matchers.doubles.plusOrMinus
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import utils.SOLUTION_HERE
import kotlin.math.pow

fun mean(xs: List<Double>): Option<Double> =
    if (xs.isEmpty()) None
    else Some(xs.sum() / xs.size())

//tag::init[]
fun variance(xs: List<Double>): Option<Double> {
    val mean = mean(xs)
    val mapped = xs.map { Some(it).flatMap { number->Some(
        (number - mean.getOrElse { 0.0 }).pow(
            2.0
        )
    ) } }.map { it.getOrElse { 0 } }

    return mean(mapped as List<Double>)
}
//end::init[]

//TODO: Enable tests by removing `!` prefix
class Exercise2 : WordSpec({

    "variance" should {
        "determine the variance of a list of numbers" {
            val ls =
                List.of(1.0, 1.1, 1.0, 3.0, 0.9, 0.4)
            variance(ls).getOrElse { 0.0 } shouldBe
                (0.675).plusOrMinus(0.005)
        }
    }
})
