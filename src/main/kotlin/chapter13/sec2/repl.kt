package chapter13.sec2

import arrow.Kind
import chapter13.ForIO
import chapter13.IO
import chapter13.IOOf
import chapter13.IORef
import chapter13.fix
import chapter5.Cons
import chapter5.Empty
import chapter5.Stream

fun IntRange.toStream(): Stream<Int> = TODO()

interface Monad<F> {

    fun <A> unit(a: A): Kind<F, A>

    fun <A, B> flatMap(fa: Kind<F, A>, f: (A) -> Kind<F, B>): Kind<F, B>

    fun <A> doWhile(
        fa: Kind<F, A>,
        cond: (A) -> Kind<F, Boolean>
    ): Kind<F, Unit>

    fun <A, B> forever(fa: Kind<F, A>): Kind<F, B>

    fun <A, B> foldM(
        sa: Stream<A>,
        z: B,
        f: (B, A) -> Kind<F, B>
    ): Kind<F, B>

    fun <A, B> foldDiscardM(
        sa: Stream<A>,
        z: B,
        f: (B, A) -> Kind<F, B>
    ): Kind<F, Unit>

    fun <A> foreachM(
        sa: Stream<A>,
        f: (A) -> Kind<F, Unit>
    ): Kind<F, Unit>

    fun <A> seq(fs: () -> Kind<F, A>): Kind<F, Unit>

    fun <A> whenM(ok: Boolean, f: () -> Kind<F, A>): Kind<F, Boolean>
}

val ioMonad = object : Monad<ForIO> {

    override fun <A> unit(a: A): IOOf<A> =
        IO.unit { a }.fix()

    override fun <A, B> flatMap(
        fa: IOOf<A>,
        f: (A) -> IOOf<B>
    ): IOOf<B> =
        fa.fix().flatMap { a -> f(a).fix() }

    //tag::init2[]
    override fun <A> doWhile( // <1>
        fa: IOOf<A>,
        cond: (A) -> IOOf<Boolean>
    ): IOOf<Unit> =
        fa.fix().flatMap { a: A ->
            cond(a).fix().flatMap<Unit> { ok: Boolean ->
                if (ok) doWhile(fa, cond).fix() else unit(Unit).fix()
            }
        }

    override fun <A, B> forever(fa: IOOf<A>): IOOf<B> { // <2>
        val t: IOOf<B> by lazy { forever<A, B>(fa) }
        return fa.fix().flatMap { t.fix() }
    }

    override fun <A, B> foldM( // <3>
        sa: Stream<A>,
        z: B,
        f: (B, A) -> IOOf<B>
    ): IOOf<B> =
        when (sa) {
            is Cons ->
                f(z, sa.head()).fix().flatMap { b ->
                    foldM(sa.tail(), z, f).fix()
                }
            is Empty -> unit(z)
        }

    override fun <A, B> foldDiscardM( // <4>
        sa: Stream<A>,
        z: B,
        f: (B, A) -> Kind<ForIO, B>
    ): Kind<ForIO, Unit> =
        foldM(sa, z, f).fix().map { Unit }

    override fun <A> foreachM( // <5>
        sa: Stream<A>,
        f: (A) -> IOOf<Unit>
    ): IOOf<Unit> =
        foldDiscardM(sa, Unit) { _, a -> f(a) }

    override fun <A> whenM( // <6>
        ok: Boolean,
        f: () -> IOOf<A>
    ): IOOf<Boolean> =
        if (ok) f().fix().map { true } else unit(false)
    //end::init2[]

    override fun <A> seq(fs: () -> IOOf<A>): IOOf<Unit> = TODO()
}

object FactorialREPL {

    val helpstring = """
        | The Amazing Factorial REPL, v0.1
        | q - quit
        | <number> - compute the factorial of the given number
        | <anything else> - bomb with horrible error
        """.trimMargin("|")

    //tag::init1[]
    private fun factorial(n: Int): IO<Int> = // <1>
        IO.ref(1).flatMap { acc: IORef<Int> -> // <2>
            ioMonad.foreachM((1..n).toStream()) { i ->
                acc.modify { it * i }.map { Unit } // <3>
            }.fix().flatMap {
                acc.get() // <4>
            }
        }

    val factorialREPL: IO<Unit> =
        ioMonad.seq {
            IO { println(helpstring) }.flatMap {
                ioMonad.doWhile(IO { readLine().orEmpty() }) { line -> // <5>
                    ioMonad.whenM(line != "q") {
                        factorial(line.toInt()).flatMap { n ->
                            IO { println("factorial: $n") }
                        }
                    }
                }.fix()
            }
        }.fix()
    //end::init1[]
}
