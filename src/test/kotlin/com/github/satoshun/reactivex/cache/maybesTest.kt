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
}
