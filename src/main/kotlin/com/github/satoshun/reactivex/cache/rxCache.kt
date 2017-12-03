package com.github.satoshun.reactivex.cache

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

internal typealias CacheMap<K, V> = ConcurrentHashMap<K, V>
internal typealias CacheValueList<V> = CopyOnWriteArrayList<V>

internal typealias CacheKey2<V1, V2> = Pair<V1, V2>
internal typealias CacheKey3<V1, V2, V3> = Triple<V1, V2, V3>
internal data class CacheKey4<V1, V2, V3, V4>(
    private val v1: V1, private val v2: V2, private val v3: V3, private val v4: V4
)

internal object MaybeComplete

internal class Either<out L : Any, out R : Any> private constructor(
    val left: L?,
    val right: R?
) {
  companion object {
    fun <L : Any, R : Any> left(left: L) = Either<L, R>(left, null)
    fun <L : Any, R : Any> right(right: R) = Either<L, R>(null, right)
  }
}
