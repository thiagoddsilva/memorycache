package io.github.memorycache

import java.io.Closeable
import java.util.Date
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.ConcurrentHashMap
import kotlin.concurrent.schedule
import kotlin.time.Duration.Companion.seconds

/**
 * MemoryCache class is a simple in-memory cache implementation.
 * It uses ConcurrentHashMap to store cache items.
 * @property options The options for the cache, can be null.
 */
class MemoryCache(private val options: CacheOptions? = null) : Closeable {
    private val cache = ConcurrentHashMap<String, CacheItem>()
    private var removeExpiredItemsTask: TimerTask

    init {
        // Schedule a task to remove expired items every second.
        removeExpiredItemsTask = Timer().schedule(Date(), 1.seconds.inWholeMilliseconds) {
            removeExpiredItems()
        }
    }

    /**
     * Removes expired items from the cache.
     */
    private fun removeExpiredItems() {
        val expiredItems = cache.filter { it.value.isExpired() }

        expiredItems.forEach {
            cache.remove(it.key)
        }
    }

    /**
     * Adds a new item to the cache.
     * If options are not null, it uses the provided options.
     * Otherwise, it uses default CacheOptions.
     *
     * @param key The key under which the item should be stored.
     * @param value The item to be stored in the cache.
     */
    fun add(key: String, value: Any) {
        if (options != null) {
            this.add(key, value, options)
            return
        }

        this.add(key, value, CacheOptions(null, null))
    }

    /**
     * Adds a new item to the cache with the specified options.
     * If absolute duration is not null, it uses absolute expiration mode.
     * If sliding duration is not null, it uses sliding expiration mode.
     * Otherwise, it uses no expiration mode.
     *
     * @param key The key under which the item should be stored.
     * @param value The item to be stored in the cache.
     * @param options The options to be used for the cache item.
     */
    fun add(key: String, value: Any, options: CacheOptions) {
        if (options.absolutDuration != null) {
            cache[key] = CacheItem(key, value, options.absolutDuration, ExpirationMode.ABSOLUTE)
            return
        }

        if (options.slidingDuration != null) {
            cache[key] = CacheItem(key, value, options.slidingDuration, ExpirationMode.SLIDING)
            return
        }

        cache[key] = CacheItem(key, value, null, ExpirationMode.NONE)
    }

    /**
     * Removes an item from the cache.
     *
     * @param key The key of the item to remove.
     */
    fun remove(key: String) {
        cache.remove(key)
    }

    /**
     * Retrieves an item from the cache.
     * If the item is found, it updates the last access time of the item.
     *
     * @param key The key of the item to retrieve.
     * @return The item if found, null otherwise.
     */
    fun get(key: String): Any? {
        val cacheItem = cache[key] ?: return null

        if (cacheItem.isExpired()) {
            return null
        }

        cacheItem.updateLastAccessTime()

        return cacheItem.value
    }

    /**
     * Retrieves a cache item from the cache.
     *
     * @param key The key of the item to retrieve.
     * @return The cache item if found, null otherwise.
     */
    fun getCacheItem(key: String): CacheItem? {
        return cache[key]
    }

    /**
     * Retrieves the count of items in the cache.
     *
     * @return The count of items in the cache.
     */
    fun getCount() : Int {
        return cache.size
    }

    /**
     * Closes the cache and cancels the task to remove expired items.
     */
    override fun close() {
        removeExpiredItemsTask.cancel()
        cache.clear()
    }
}