package com.github.satoshun.reactivex.cache

import io.reactivex.Flowable
import io.reactivex.Single
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class FlowablesTest {
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
    val error = Exception("hoge")
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
}
