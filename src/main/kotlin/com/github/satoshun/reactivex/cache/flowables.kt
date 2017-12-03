package com.github.satoshun.reactivex.cache

import io.reactivex.Flowable

fun <P1, R> rxCache(original: Function1<P1, Flowable<R>>): FlowableCache1<P1, R> {
  return FlowableCache1(original)
}

class FlowableCache1<in P1, R>(
    private val original: Function1<P1, Flowable<R>>
) : Function1<P1, Flowable<R>> {
  private val cache = CacheMap<P1, CacheValueList<R>>()

  override operator fun invoke(p1: P1): Flowable<R> {
    val value = cache[p1]
    return if (value != null) Flowable.fromIterable(value)
    else forceInvalidation(p1)
  }

  fun forceInvalidation(p1: P1): Flowable<R> {
    val cacheList = CacheValueList<R>()
    return original(p1).doOnNext { cacheList += it }
        .doOnComplete { cache[p1] = cacheList }
  }
}
