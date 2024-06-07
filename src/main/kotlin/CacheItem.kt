package io.github.memorycache

import kotlin.time.Duration

class CacheItem(val key: String, val value: Any, private val duration: Duration?) {
    private val creationTime = System.currentTimeMillis()

    fun isExpired(): Boolean {
        if (duration == null) {
            return false
        }

        return System.currentTimeMillis() - creationTime > duration.inWholeMilliseconds
    }
}