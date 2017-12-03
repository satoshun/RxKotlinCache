package com.github.satoshun.reactivex.cache

import io.reactivex.Single

fun <P1, R : Any> rxCache(original: Function1<P1, Single<R>>): SingleCache1<P1, R> {
  return SingleCache1(original)
}

class SingleCache1<in P1, R : Any>(
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

fun <P1, P2, R : Any> rxCache(original: Function2<P1, P2, Single<R>>): SingleCache2<P1, P2, R> {
  return SingleCache2(original)
}

class SingleCache2<in P1, in P2, R : Any>(
    private val original: Function2<P1, P2, Single<R>>
) : Function2<P1, P2, Single<R>> {
  private val cache = CacheMap<CacheKey2<P1, P2>, R>()

  override operator fun invoke(p1: P1, p2: P2): Single<R> {
    val key = CacheKey2(p1, p2)
    val value = cache[key]
    return if (value != null) Single.just(value)
    else forceInvalidation(p1, p2)
  }

  fun forceInvalidation(p1: P1, p2: P2): Single<R> {
    val key = CacheKey2(p1, p2)
    return original(p1, p2).doOnSuccess { cache[key] = it }
  }
}

fun <P1, P2, P3, R : Any> rxCache(original: Function3<P1, P2, P3, Single<R>>): SingleCache3<P1, P2, P3, R> {
  return SingleCache3(original)
}

class SingleCache3<in P1, in P2, in P3, R : Any>(
    private val original: Function3<P1, P2, P3, Single<R>>
) : Function3<P1, P2, P3, Single<R>> {
  private val cache = CacheMap<CacheKey3<P1, P2, P3>, R>()

  override operator fun invoke(p1: P1, p2: P2, p3: P3): Single<R> {
    val key = CacheKey3(p1, p2, p3)
    val value = cache[key]
    return if (value != null) Single.just(value)
    else forceInvalidation(p1, p2, p3)
  }

  fun forceInvalidation(p1: P1, p2: P2, p3: P3): Single<R> {
    val key = CacheKey3(p1, p2, p3)
    return original(p1, p2, p3).doOnSuccess { cache[key] = it }
  }
}

fun <P1, P2, P3, P4, R : Any> rxCache(original: Function4<P1, P2, P3, P4, Single<R>>): SingleCache4<P1, P2, P3, P4, R> {
  return SingleCache4(original)
}

class SingleCache4<in P1, in P2, in P3, in P4, R : Any>(
    private val original: Function4<P1, P2, P3, P4, Single<R>>
) : Function4<P1, P2, P3, P4, Single<R>> {
  private val cache = CacheMap<CacheKey4<P1, P2, P3, P4>, R>()

  override operator fun invoke(p1: P1, p2: P2, p3: P3, p4: P4): Single<R> {
    val key = CacheKey4(p1, p2, p3, p4)
    val value = cache[key]
    return if (value != null) Single.just(value)
    else forceInvalidation(p1, p2, p3, p4)
  }

  fun forceInvalidation(p1: P1, p2: P2, p3: P3, p4: P4): Single<R> {
    val key = CacheKey4(p1, p2, p3, p4)
    return original(p1, p2, p3, p4).doOnSuccess { cache[key] = it }
  }
}
