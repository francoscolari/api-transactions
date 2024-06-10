package com.mendel.api.transactions.adapter.cache

import com.mendel.api.transactions.adapter.cache.model.TransactionEntity
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class InMemoryRepository : CacheRepository<Long, String, TransactionEntity> {
    private val store = ConcurrentHashMap<Long, TransactionEntity>()
    private val childrenMap = mutableMapOf<Long, MutableList<TransactionEntity>>()

    override fun get(key: Long): TransactionEntity? {
        return store[key]
    }

    override fun set(key: Long, value: TransactionEntity): TransactionEntity {
        store[key] = value
        updateChildrenMap(value)
        return value
    }

    override fun findByType(type: String): List<TransactionEntity> {
        return store.values.filter { it.type == type }
    }

    override fun findById(key: Long): List<TransactionEntity>? {
        val result = mutableListOf<TransactionEntity>()
        val root = store[key] ?: return null
        findChildrenRecursive(root, result)
        return result
    }

    private fun findChildrenRecursive(entity: TransactionEntity, result: MutableList<TransactionEntity>) {
        result.add(entity)
        val children = childrenMap[entity.id] ?: emptyList()
        for (child in children) {
            findChildrenRecursive(child, result)
        }
    }

    private fun updateChildrenMap(entity: TransactionEntity) {
        if (entity.parentId != null) {
            childrenMap.computeIfAbsent(entity.parentId) { mutableListOf() }.add(entity)
        }
    }
}
