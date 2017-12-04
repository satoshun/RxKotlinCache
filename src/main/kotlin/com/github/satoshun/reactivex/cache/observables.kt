package com.github.satoshun.reactivex.cache

import io.reactivex.Observable

fun <P1, R : Any> rxCache(original: Function1<P1, Observable<R>>): ObservableCache1<P1, R> {
  return ObservableCache1(original)
}

class ObservableCache1<in P1, R : Any>(
    private val original: Function1<P1, Observable<R>>
) : Function1<P1, Observable<R>> {
  private val cache = CacheMap<P1, CacheValueList<R>>()

  override operator fun invoke(p1: P1): Observable<R> {
    val value = cache[p1]
    return if (value != null) Observable.fromIterable(value)
    else forceInvalidation(p1)
  }

  fun forceInvalidation(p1: P1): Observable<R> {
    val cacheList = CacheValueList<R>()
    return original(p1).doOnNext { cacheList += it }
        .doOnComplete { cache[p1] = cacheList }
  }
}

fun <P1, P2, R : Any> rxCache(original: Function2<P1, P2, Observable<R>>): ObservableCache2<P1, P2, R> {
  return ObservableCache2(original)
}

class ObservableCache2<in P1, in P2, R : Any>(
    private val original: Function2<P1, P2, Observable<R>>
) : Function2<P1, P2, Observable<R>> {
  private val cache = CacheMap<CacheKey2<P1, P2>, CacheValueList<R>>()

  override operator fun invoke(p1: P1, p2: P2): Observable<R> {
    val value = cache[CacheKey2(p1, p2)]
    return if (value != null) Observable.fromIterable(value)
    else forceInvalidation(p1, p2)
  }

  fun forceInvalidation(p1: P1, p2: P2): Observable<R> {
    val key = CacheKey2(p1, p2)
    val cacheList = CacheValueList<R>()
    return original(p1, p2).doOnNext { cacheList += it }
        .doOnComplete { cache[key] = cacheList }
  }
}

fun <P1, P2, P3, R : Any> rxCache(original: Function3<P1, P2, P3, Observable<R>>): ObservableCache3<P1, P2, P3, R> {
  return ObservableCache3(original)
}

class ObservableCache3<in P1, in P2, in P3, R : Any>(
    private val original: Function3<P1, P2, P3, Observable<R>>
) : Function3<P1, P2, P3, Observable<R>> {
  private val cache = CacheMap<CacheKey3<P1, P2, P3>, CacheValueList<R>>()

  override operator fun invoke(p1: P1, p2: P2, p3: P3): Observable<R> {
    val value = cache[CacheKey3(p1, p2, p3)]
    return if (value != null) Observable.fromIterable(value)
    else forceInvalidation(p1, p2, p3)
  }

  fun forceInvalidation(p1: P1, p2: P2, p3: P3): Observable<R> {
    val key = CacheKey3(p1, p2, p3)
    val cacheList = CacheValueList<R>()
    return original(p1, p2, p3).doOnNext { cacheList += it }
        .doOnComplete { cache[key] = cacheList }
  }
}

fun <P1, P2, P3, P4, R : Any> rxCache(original: Function4<P1, P2, P3, P4, Observable<R>>): ObservableCache4<P1, P2, P3, P4, R> {
  return ObservableCache4(original)
}

class ObservableCache4<in P1, in P2, in P3, in P4, R : Any>(
    private val original: Function4<P1, P2, P3, P4, Observable<R>>
) : Function4<P1, P2, P3, P4, Observable<R>> {
  private val cache = CacheMap<CacheKey4<P1, P2, P3, P4>, CacheValueList<R>>()

  override operator fun invoke(p1: P1, p2: P2, p3: P3, p4: P4): Observable<R> {
    val value = cache[CacheKey4(p1, p2, p3, p4)]
    return if (value != null) Observable.fromIterable(value)
    else forceInvalidation(p1, p2, p3, p4)
  }

  fun forceInvalidation(p1: P1, p2: P2, p3: P3, p4: P4): Observable<R> {
    val key = CacheKey4(p1, p2, p3, p4)
    val cacheList = CacheValueList<R>()
    return original(p1, p2, p3, p4).doOnNext { cacheList += it }
        .doOnComplete { cache[key] = cacheList }
  }
}