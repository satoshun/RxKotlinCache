package com.github.satoshun.reactivex.cache

import java.util.concurrent.ConcurrentHashMap

internal typealias CacheMap<K, V> = ConcurrentHashMap<K, V>

internal typealias CacheKey2<V1, V2> = Pair<V1, V2>
