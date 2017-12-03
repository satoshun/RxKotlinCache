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
    val wrapped = rxCache(original)

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
    val wrapped = rxCache(original)

    wrapped(1000).subscribe({}, {})
    assertThat(counter, `is`(1000))
    wrapped(1000).subscribe({}, {})
    assertThat(counter, `is`(2000))
  }

  @Test
  fun cache2() {
    var counter = 0
    val original = object : Function2<Int, Int, Single<String>> {
      override fun invoke(p1: Int, p2: Int): Single<String> {
        counter += p1 + p2
        return Single.just(p1.toString())
      }
    }
    val wrapped = rxCache(original)

    wrapped(1, 2).subscribe()
    assertThat(counter, `is`(3))
    wrapped(2, 3).subscribe()
    assertThat(counter, `is`(8))

    wrapped(1, 2).subscribe()
    assertThat(counter, `is`(8))

    wrapped.forceInvalidation(1, 2).subscribe()
    assertThat(counter, `is`(11))
  }

  @Test
  fun cache2_exception() {
    var counter = 0
    val original = object : Function2<Int, Int, Single<String>> {
      override fun invoke(p1: Int, p2: Int): Single<String> {
        counter += p1 + p2
        return Single.error(Exception())
      }
    }
    val wrapped = rxCache(original)

    wrapped(1, 2).subscribe({}, {})
    assertThat(counter, `is`(3))
    wrapped(1, 2).subscribe({}, {})
    assertThat(counter, `is`(6))
  }
}
