package com.github.satoshun.reactivex.cache

import io.reactivex.Flowable
import io.reactivex.Single
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class FlowablesTest {
  private val error = Exception("error")

  @Test
  fun cache1() {
    var counter = 0
    val original = object : Function1<Int, Flowable<String>> {
      override fun invoke(p1: Int): Flowable<String> {
        counter += p1
        return Flowable.just(p1.toString(), (p1 + 1).toString())
      }
    }
    val wrapped = rxCache(original)

    wrapped(1).test().assertValues("1", "2")
    assertThat(counter, `is`(1))

    wrapped(2).test().assertValues("2", "3")
    assertThat(counter, `is`(3))

    wrapped(1).test().assertValues("1", "2")
    assertThat(counter, `is`(3))

    wrapped.forceInvalidation(1).test().assertValues("1", "2")
    assertThat(counter, `is`(4))
  }

  @Test
  fun cache1_exception() {
    var counter = 0
    val original = object : Function1<Int, Flowable<String>> {
      override fun invoke(p1: Int): Flowable<String> {
        counter += p1
        return Flowable.error(error)
      }
    }
    val wrapped = rxCache(original)

    wrapped(1).test().assertError(error)
    assertThat(counter, `is`(1))
    wrapped(1).test().assertError(error)
    assertThat(counter, `is`(2))
  }

  @Test
  fun cache2() {
    var counter = 0
    val original = object : Function2<Int, Int, Flowable<String>> {
      override fun invoke(p1: Int, p2: Int): Flowable<String> {
        counter += p1 + p2
        return Flowable.just(p1.toString(), (p1 + 1).toString())
      }
    }
    val wrapped = rxCache(original)

    wrapped(1, 2).test().assertValues("1", "2")
    assertThat(counter, `is`(3))

    wrapped(2, 4).test().assertValues("2", "3")
    assertThat(counter, `is`(9))

    wrapped(1, 2).test().assertValues("1", "2")
    assertThat(counter, `is`(9))

    wrapped.forceInvalidation(1, 2).test().assertValues("1", "2")
    assertThat(counter, `is`(12))
  }

  @Test
  fun cache2_exception() {
    var counter = 0
    val original = object : Function2<Int, Int, Flowable<String>> {
      override fun invoke(p1: Int, p2: Int): Flowable<String> {
        counter += p1 + p2
        return Flowable.error(error)
      }
    }
    val wrapped = rxCache(original)

    wrapped(1, 2).test().assertError(error)
    assertThat(counter, `is`(3))
    wrapped(1, 2).test().assertError(error)
    assertThat(counter, `is`(6))
  }

  @Test
  fun cache3() {
    var counter = 0
    val original = object : Function3<Int, Int, Int, Flowable<String>> {
      override fun invoke(p1: Int, p2: Int, p3: Int): Flowable<String> {
        counter += p1 + p2 + p3
        return Flowable.just(p1.toString(), (p1 + 1).toString())
      }
    }
    val wrapped = rxCache(original)

    wrapped(1, 2, 4).test().assertValues("1", "2")
    assertThat(counter, `is`(7))

    wrapped(2, 4, 7).test().assertValues("2", "3")
    assertThat(counter, `is`(20))

    wrapped(1, 2, 4).test().assertValues("1", "2")
    assertThat(counter, `is`(20))

    wrapped.forceInvalidation(1, 2, 4).test().assertValues("1", "2")
    assertThat(counter, `is`(27))
  }

  @Test
  fun cache3_exception() {
    var counter = 0
    val original = object : Function3<Int, Int, Int, Flowable<String>> {
      override fun invoke(p1: Int, p2: Int, p3: Int): Flowable<String> {
        counter += p1 + p2 + p3
        return Flowable.error(error)
      }
    }
    val wrapped = rxCache(original)

    wrapped(1, 2, 4).test().assertError(error)
    assertThat(counter, `is`(7))
    wrapped(1, 2, 4).test().assertError(error)
    assertThat(counter, `is`(14))
  }

  @Test
  fun cache4() {
    var counter = 0
    val original = object : Function4<Int, Int, Int, Int, Flowable<String>> {
      override fun invoke(p1: Int, p2: Int, p3: Int, p4: Int): Flowable<String> {
        counter += p1 + p2 + p3 + p4
        return Flowable.just(p1.toString(), (p1 + 1).toString())
      }
    }
    val wrapped = rxCache(original)

    wrapped(1, 2, 4, 7).test().assertValues("1", "2")
    assertThat(counter, `is`(14))

    wrapped(2, 4, 7, 11).test().assertValues("2", "3")
    assertThat(counter, `is`(38))

    wrapped(1, 2, 4, 7).test().assertValues("1", "2")
    assertThat(counter, `is`(38))

    wrapped.forceInvalidation(1, 2, 4, 7).test().assertValues("1", "2")
    assertThat(counter, `is`(52))
  }

  @Test
  fun cache4_exception() {
    var counter = 0
    val original = object : Function4<Int, Int, Int, Int, Flowable<String>> {
      override fun invoke(p1: Int, p2: Int, p3: Int, p4: Int): Flowable<String> {
        counter += p1 + p2 + p3 + p4
        return Flowable.error(error)
      }
    }
    val wrapped = rxCache(original)

    wrapped(1, 2, 4, 7).test().assertError(error)
    assertThat(counter, `is`(14))
    wrapped(1, 2, 4, 7).test().assertError(error)
    assertThat(counter, `is`(28))
  }
}
