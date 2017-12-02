package com.github.satoshun.reactivex.cache

import io.reactivex.Single

fun <P1, R> RxCache(original: Function1<P1, Single<R>>): SingleCache1<P1, R> {
  return SingleCache1(original)
}

class SingleCache1<in P1, R>(
    private val original: Function1<P1, Single<R>>
) : Function1<P1, Single<R>> {
  private val cache = CacheMap<P1, R>()

  override operator fun invoke(p1: P1): Single<R> {
    val value = cache[p1]
    return if (value != null) Single.just(value)
    else forceInvalidation(p1)
  }

  fun forceInvalidation(p1: P1): Single<R> {
    return original(p1).doOnSuccess { cache[p1] = it }
  }
}

