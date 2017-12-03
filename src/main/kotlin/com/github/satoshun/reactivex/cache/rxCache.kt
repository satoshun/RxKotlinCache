package com.github.satoshun.reactivex.cache

import java.util.concurrent.ConcurrentHashMap

internal typealias CacheMap<K, V> = ConcurrentHashMap<K, V>

internal typealias CacheKey2<V1, V2> = Pair<V1, V2>
internal typealias CacheKey3<V1, V2, V3> = Triple<V1, V2, V3>
internal data class CacheKey4<V1, V2, V3, V4>(
    private val v1: V1, private val v2: V2, private val v3: V3, private val v4: V4
)
