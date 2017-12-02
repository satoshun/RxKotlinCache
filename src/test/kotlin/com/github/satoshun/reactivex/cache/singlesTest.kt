package com.github.satoshun.reactivex.cache

import io.reactivex.Single
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class SinglesTest {
  @Test
  fun cache1() {
    var counter = 0
    val original = object : Function1<Int, Single<String>> {
      override fun invoke(p1: Int): Single<String> {
        counter += p1
        return Single.just(p1.toString())
      }
    }
    val wrapped = RxCache(original)

    wrapped(1000).subscribe()
    assertThat(counter, `is`(1000))
    wrapped(1001).subscribe()
    assertThat(counter, `is`(2001))

    wrapped(1000).subscribe()
    assertThat(counter, `is`(2001))

    wrapped.forceInvalidation(1000).subscribe()
    assertThat(counter, `is`(3001))
  }

  @Test
  fun cache1_exception() {
    var counter = 0
    val original = object : Function1<Int, Single<String>> {
      override fun invoke(p1: Int): Single<String> {
        counter += p1
        return Single.error(Exception("hoge"))
      }
    }
    val wrapped = RxCache(original)

    wrapped(1000).subscribe({}, {})
    assertThat(counter, `is`(1000))
    wrapped(1000).subscribe({}, {})
    assertThat(counter, `is`(2000))
  }
}
