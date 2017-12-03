package com.github.satoshun.reactivex.cache

import io.reactivex.Maybe
import io.reactivex.Single
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class MaybesTest {
  private val error = Exception("error")

  @Test
  fun cache1_success() {
    var counter = 0
    val original = object : Function1<Int, Maybe<String>> {
      override fun invoke(p1: Int): Maybe<String> {
        counter += p1
        return Maybe.just(p1.toString())
      }
    }
    val wrapped = rxCache(original)

    wrapped(1000).test().assertValue("1000")
    assertThat(counter, `is`(1000))
    wrapped(1001).test().assertValue("1001")
    assertThat(counter, `is`(2001))

    wrapped(1000).test().assertValue("1000")
    assertThat(counter, `is`(2001))

    wrapped.forceInvalidation(1000).test().assertValue("1000")
    assertThat(counter, `is`(3001))
  }

  @Test
  fun cache1_complete() {
    var counter = 0
    val original = object : Function1<Int, Maybe<String>> {
      override fun invoke(p1: Int): Maybe<String> {
        counter += p1
        return Maybe.empty()
      }
    }
    val wrapped = rxCache(original)

    wrapped(1000).test().assertComplete()
    assertThat(counter, `is`(1000))
    wrapped(1001).test().assertComplete()
    assertThat(counter, `is`(2001))

    wrapped(1000).test().assertComplete()
    assertThat(counter, `is`(2001))

    wrapped.forceInvalidation(1000).test().assertComplete()
    assertThat(counter, `is`(3001))
  }

  @Test
  fun cache1_exception() {
    var counter = 0
    val original = object : Function1<Int, Maybe<String>> {
      override fun invoke(p1: Int): Maybe<String> {
        counter += p1
        return Maybe.error(error)
      }
    }
    val wrapped = rxCache(original)

    wrapped(1000).test().assertError(error)
    assertThat(counter, `is`(1000))
    wrapped(1000).test().assertError(error)
    assertThat(counter, `is`(2000))
  }

  @Test
  fun cache2_success() {
    var counter = 0
    val original = object : Function2<Int, Int, Maybe<String>> {
      override fun invoke(p1: Int, p2: Int): Maybe<String> {
        counter += p1 + p2
        return Maybe.just(p1.toString())
      }
    }
    val wrapped = rxCache(original)

    wrapped(1, 2).test().assertValue("1")
    assertThat(counter, `is`(3))
    wrapped(2, 3).test().assertValue("2")
    assertThat(counter, `is`(8))

    wrapped(1, 2).test().assertValue("1")
    assertThat(counter, `is`(8))

    wrapped.forceInvalidation(1, 2).test().assertValue("1")
    assertThat(counter, `is`(11))
  }

  @Test
  fun cache2_complete() {
    var counter = 0
    val original = object : Function2<Int, Int, Maybe<String>> {
      override fun invoke(p1: Int, p2: Int): Maybe<String> {
        counter += p1 + p2
        return Maybe.empty()
      }
    }
    val wrapped = rxCache(original)

    wrapped(1, 2).test().assertComplete()
    assertThat(counter, `is`(3))
    wrapped(2, 3).test().assertComplete()
    assertThat(counter, `is`(8))

    wrapped(1, 2).test().assertComplete()
    assertThat(counter, `is`(8))

    wrapped.forceInvalidation(1, 2).test().assertComplete()
    assertThat(counter, `is`(11))
  }

  @Test
  fun cache2_exception() {
    var counter = 0
    val original = object : Function2<Int, Int, Maybe<String>> {
      override fun invoke(p1: Int, p2: Int): Maybe<String> {
        counter += p1 + p2
        return Maybe.error(error)
      }
    }
    val wrapped = rxCache(original)

    wrapped(1, 2).test().assertError(error)
    assertThat(counter, `is`(3))
    wrapped(1, 2).test().assertError(error)
    assertThat(counter, `is`(6))
  }
}
