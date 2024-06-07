package io.github.memorycache

import java.util.concurrent.ConcurrentHashMap

class MemoryCache {
    private val cache = ConcurrentHashMap<String, CacheItem>()

    /**
     * Adds a new item to the cache.
     *
     * @param key The key under which the item should be stored.
     * @param value The item to be stored in the cache.
     */
    fun add(key: String, value: Any) {
        cache[key] = CacheItem(key, value, null)
    }

    /**
     * Retrieves an item from the cache.
     *
     * @param key The key of the item to retrieve.
     * @return The item if found, null otherwise.
     */
    fun get(key: String): Any? {
        val cacheItem = cache[key] ?: return null

        return cacheItem.value
    }
}