package com.mendel.api.transactions.adapter.cache

interface CacheRepository<K, T, V> {
    fun set(key: K, value: V): V
    fun get(key: K): V?
    fun findByType(type: T): List<V>
    fun findById(type: K): List<V>?
}
