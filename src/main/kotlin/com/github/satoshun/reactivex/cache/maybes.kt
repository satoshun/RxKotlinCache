package com.github.satoshun.reactivex.cache

import io.reactivex.Maybe

fun <P1, R : Any> rxCache(original: Function1<P1, Maybe<R>>): MaybeCache1<P1, R> {
  return MaybeCache1(original)
}

class MaybeCache1<in P1, R : Any>(
    private val original: Function1<P1, Maybe<R>>
) : Function1<P1, Maybe<R>> {
  private val cache = CacheMap<P1, Either<R, MaybeComplete>>()

  override operator fun invoke(p1: P1): Maybe<R> {
    val value = cache[p1]
    return value?.toMaybe() ?: forceInvalidation(p1)
  }

  fun forceInvalidation(p1: P1): Maybe<R> {
    return original(p1)
        .doOnSuccess { cache[p1] = Either.left(it) }
        .doOnComplete { cache[p1] = Either.right(MaybeComplete) }
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

private fun <R : Any> Either<R, MaybeComplete>.toMaybe(): Maybe<R> {
  return if (left != null) Maybe.just(left)
  else Maybe.empty()
}
