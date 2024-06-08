package io.github.memorycache

import kotlin.time.Duration

enum class ExpirationMode {
    ABSOLUTE,
    SLIDING,
    NONE
}

class CacheItem(val key: String,
                val value: Any,
                val duration: Duration?,
                val expirationMode: ExpirationMode) {

    private val creationTime = System.currentTimeMillis()
    private var lastAccessTime = creationTime

    fun isExpired(): Boolean {
        if (duration == null) {
            return false
        }

        if (expirationMode == ExpirationMode.SLIDING) {
            return System.currentTimeMillis() - lastAccessTime > duration.inWholeMilliseconds
        }

        return System.currentTimeMillis() - creationTime > duration.inWholeMilliseconds
    }

    fun updateLastAccessTime() {
        lastAccessTime = System.currentTimeMillis()
    }
}