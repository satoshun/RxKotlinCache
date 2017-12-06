package com.github.satoshun.reactivex.cache

import io.reactivex.Maybe

fun <R : Any> rxCache(original: Function0<Maybe<R>>): MaybeCache0<R> {
  return MaybeCache0(original)
}

class MaybeCache0<R : Any>(
    private val original: Function0<Maybe<R>>
) : Function0<Maybe<R>> {
  private var cache: Either<R, MaybeComplete>? = null

  override operator fun invoke(): Maybe<R> {
    val value = cache
    return value?.toMaybe() ?: forceInvalidation()
  }

  fun forceInvalidation(): Maybe<R> {
    return original()
        .doOnSuccess { cache = Either.left(it) }
        .doOnComplete { cache = Either.right(MaybeComplete) }
  }
}

fun <P1, R : Any> rxCache(original: Function1<P1, Maybe<R>>): MaybeCache1<P1, R> {
  return MaybeCache1(original)
}

class MaybeCache1<in P1, R : Any>(
    private val original: Function1<P1, Maybe<R>>
) : Function1<P1, Maybe<R>> {
  private val cache = CacheMap<P1, Either<R, MaybeComplete>>()

  override operator fun invoke(p1: P1): Maybe<R> {
    val value = if (p1 == null) null else cache[p1]
    return value?.toMaybe() ?: forceInvalidation(p1)
  }

  fun forceInvalidation(p1: P1): Maybe<R> {
    return original(p1)
        .doOnSuccess { if (p1 != null) cache[p1] = Either.left(it) }
        .doOnComplete { if (p1 != null) cache[p1] = Either.right(MaybeComplete) }
  }
}

fun <P1, P2, R : Any> rxCache(original: Function2<P1, P2, Maybe<R>>): MaybeCache2<P1, P2, R> {
  return MaybeCache2(original)
}

class MaybeCache2<in P1, in P2, R : Any>(
    private val original: Function2<P1, P2, Maybe<R>>
) : Function2<P1, P2, Maybe<R>> {
  private val cache = CacheMap<CacheKey2<P1, P2>, Either<R, MaybeComplete>>()

  override operator fun invoke(p1: P1, p2: P2): Maybe<R> {
    val value = cache[CacheKey2(p1, p2)]
    return value?.toMaybe() ?: forceInvalidation(p1, p2)
  }

  fun forceInvalidation(p1: P1, p2: P2): Maybe<R> {
    val key = CacheKey2(p1, p2)
    return original(p1, p2)
        .doOnSuccess { cache[key] = Either.left(it) }
        .doOnComplete { cache[key] = Either.right(MaybeComplete) }
  }
}

fun <P1, P2, P3, R : Any> rxCache(original: Function3<P1, P2, P3, Maybe<R>>): MaybeCache3<P1, P2, P3, R> {
  return MaybeCache3(original)
}

class MaybeCache3<in P1, in P2, in P3, R : Any>(
    private val original: Function3<P1, P2, P3, Maybe<R>>
) : Function3<P1, P2, P3, Maybe<R>> {
  private val cache = CacheMap<CacheKey3<P1, P2, P3>, Either<R, MaybeComplete>>()

  override operator fun invoke(p1: P1, p2: P2, p3: P3): Maybe<R> {
    val value = cache[CacheKey3(p1, p2, p3)]
    return value?.toMaybe() ?: forceInvalidation(p1, p2, p3)
  }

  fun forceInvalidation(p1: P1, p2: P2, p3: P3): Maybe<R> {
    val key = CacheKey3(p1, p2, p3)
    return original(p1, p2, p3)
        .doOnSuccess { cache[key] = Either.left(it) }
        .doOnComplete { cache[key] = Either.right(MaybeComplete) }
  }
}

fun <P1, P2, P3, P4, R : Any> rxCache(original: Function4<P1, P2, P3, P4, Maybe<R>>): MaybeCache4<P1, P2, P3, P4, R> {
  return MaybeCache4(original)
}

class MaybeCache4<in P1, in P2, in P3, in P4, R : Any>(
    private val original: Function4<P1, P2, P3, P4, Maybe<R>>
) : Function4<P1, P2, P3, P4, Maybe<R>> {
  private val cache = CacheMap<CacheKey4<P1, P2, P3, P4>, Either<R, MaybeComplete>>()

  override operator fun invoke(p1: P1, p2: P2, p3: P3, p4: P4): Maybe<R> {
    val value = cache[CacheKey4(p1, p2, p3, p4)]
    return value?.toMaybe() ?: forceInvalidation(p1, p2, p3, p4)
  }

  fun forceInvalidation(p1: P1, p2: P2, p3: P3, p4: P4): Maybe<R> {
    val key = CacheKey4(p1, p2, p3, p4)
    return original(p1, p2, p3, p4)
        .doOnSuccess { cache[key] = Either.left(it) }
        .doOnComplete { cache[key] = Either.right(MaybeComplete) }
  }
}

private fun <R : Any> Either<R, MaybeComplete>.toMaybe(): Maybe<R> {
  return if (left != null) Maybe.just(left)
  else Maybe.empty()
}
