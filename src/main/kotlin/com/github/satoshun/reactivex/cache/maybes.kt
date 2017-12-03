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

private fun <R : Any> Either<R, MaybeComplete>.toMaybe(): Maybe<R> {
  return if (left != null) Maybe.just(left)
  else Maybe.empty()
}
