package com.github.satoshun.reactivex.cache

import io.reactivex.Flowable

fun <P1, R : Any> rxCache(original: Function1<P1, Flowable<R>>): FlowableCache1<P1, R> {
  return FlowableCache1(original)
}

class FlowableCache1<in P1, R : Any>(
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

fun <P1, P2, R : Any> rxCache(original: Function2<P1, P2, Flowable<R>>): FlowableCache2<P1, P2, R> {
  return FlowableCache2(original)
}

class FlowableCache2<in P1, in P2, R : Any>(
    private val original: Function2<P1, P2, Flowable<R>>
) : Function2<P1, P2, Flowable<R>> {
  private val cache = CacheMap<CacheKey2<P1, P2>, CacheValueList<R>>()

  override operator fun invoke(p1: P1, p2: P2): Flowable<R> {
    val value = cache[CacheKey2(p1, p2)]
    return if (value != null) Flowable.fromIterable(value)
    else forceInvalidation(p1, p2)
  }

  fun forceInvalidation(p1: P1, p2: P2): Flowable<R> {
    val key = CacheKey2(p1, p2)
    val cacheList = CacheValueList<R>()
    return original(p1, p2).doOnNext { cacheList += it }
        .doOnComplete { cache[key] = cacheList }
  }
}

fun <P1, P2, P3, R : Any> rxCache(original: Function3<P1, P2, P3, Flowable<R>>): FlowableCache3<P1, P2, P3, R> {
  return FlowableCache3(original)
}

class FlowableCache3<in P1, in P2, in P3, R : Any>(
    private val original: Function3<P1, P2, P3, Flowable<R>>
) : Function3<P1, P2, P3, Flowable<R>> {
  private val cache = CacheMap<CacheKey3<P1, P2, P3>, CacheValueList<R>>()

  override operator fun invoke(p1: P1, p2: P2, p3: P3): Flowable<R> {
    val value = cache[CacheKey3(p1, p2, p3)]
    return if (value != null) Flowable.fromIterable(value)
    else forceInvalidation(p1, p2, p3)
  }

  fun forceInvalidation(p1: P1, p2: P2, p3: P3): Flowable<R> {
    val key = CacheKey3(p1, p2, p3)
    val cacheList = CacheValueList<R>()
    return original(p1, p2, p3).doOnNext { cacheList += it }
        .doOnComplete { cache[key] = cacheList }
  }
}

fun <P1, P2, P3, P4, R : Any> rxCache(original: Function4<P1, P2, P3, P4, Flowable<R>>): FlowableCache4<P1, P2, P3, P4, R> {
  return FlowableCache4(original)
}

class FlowableCache4<in P1, in P2, in P3, in P4, R : Any>(
    private val original: Function4<P1, P2, P3, P4, Flowable<R>>
) : Function4<P1, P2, P3, P4, Flowable<R>> {
  private val cache = CacheMap<CacheKey4<P1, P2, P3, P4>, CacheValueList<R>>()

  override operator fun invoke(p1: P1, p2: P2, p3: P3, p4: P4): Flowable<R> {
    val value = cache[CacheKey4(p1, p2, p3, p4)]
    return if (value != null) Flowable.fromIterable(value)
    else forceInvalidation(p1, p2, p3, p4)
  }

  fun forceInvalidation(p1: P1, p2: P2, p3: P3, p4: P4): Flowable<R> {
    val key = CacheKey4(p1, p2, p3, p4)
    val cacheList = CacheValueList<R>()
    return original(p1, p2, p3, p4).doOnNext { cacheList += it }
        .doOnComplete { cache[key] = cacheList }
  }
}
