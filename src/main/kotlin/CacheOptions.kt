package io.github.memorycache

import kotlin.time.Duration

data class CacheOptions(
    val absolutDuration: Duration? = null,
    val slidingDuration: Duration? = null)