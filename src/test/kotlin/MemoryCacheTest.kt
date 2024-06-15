import io.github.thiagoddsilva.memorycache.CacheOptions
import io.github.thiagoddsilva.memorycache.ExpirationMode
import io.github.thiagoddsilva.memorycache.MemoryCache
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

internal class MemoryCacheTest {

    @Test
    fun init_whenCalled_shouldReturnNewObject() {
        val cache = MemoryCache()
        assertNotNull(cache)
    }

    @Test
    fun add_whenAddAnItemInCache_shouldReturnItem() {
        val cache = MemoryCache()
        cache.add("key", "value")

        val item = cache.get("key")

        assertNotNull(item)
        assertEquals("value", item)
    }

    @Test
    fun add_whenAddAnItemInCacheWithAbsoluteDuration_shouldReturnItem() {
        val cache = MemoryCache()
        cache.add("key", "value", CacheOptions(1.seconds, null))

        val item = cache.get("key")

        assertNotNull(item)
        assertEquals("value", item)
    }

    @Test
    fun add_whenAddAnItemInCacheWithSlidingDuration_shouldReturnItem() {
        val cache = MemoryCache()
        cache.add("key", "value", CacheOptions(null, 1.seconds))

        val item = cache.get("key")

        assertNotNull(item)
        assertEquals("value", item)
    }

    @Test
    fun add_whenAddAnItemInCacheWithDefaultCacheOptions_shouldReturnItem() {
        val cache = MemoryCache(CacheOptions(1.seconds, null))
        cache.add("key", "value")

        val item = cache.getCacheItem("key")

        assertNotNull(item)
        assertEquals(ExpirationMode.ABSOLUTE, item.expirationMode)
        assertEquals(1.seconds, item.duration)
    }

    @Test
    fun get_whenGetAnItemFromCache_shouldReturnItem() {
        val cache = MemoryCache()
        cache.add("key", "value")

        val item = cache.get("key")

        assertNotNull(item)
        assertEquals("value", item)
    }

    @Test
    fun remove_whenRemoveAnItemFromCache_shouldReturnNull() {
        val cache = MemoryCache()
        cache.add("key", "value")

        cache.remove("key")

        val item = cache.get("key")

        assertEquals(null, item)
    }

    @Test
    fun getCount_whenAddItemsInCache_shouldReturnCount() {
        val cache = MemoryCache()
        cache.add("key1", "value1")
        cache.add("key2", "value2")
        cache.add("key3", "value3")

        val count = cache.getCount()

        assertEquals(3, count)
    }

    @Test
    fun addAndGet_whenGetExpiredItemFromCache_shouldReturnNull() {
        val cache = MemoryCache()
        cache.add("key", "value", CacheOptions(100.milliseconds, null))

        Thread.sleep(110)

        val item = cache.get("key")

        assertEquals(null, item)
    }

    @Test
    fun addAndGet_whenGetExpiredItemFromCache_shouldReturnNullAfterItemRemovedByCleanRoutine() {
        val cache = MemoryCache()
        cache.add("key", "value", CacheOptions(1.seconds, null))

        Thread.sleep(1100)

        val item = cache.get("key")

        assertEquals(null, item)
    }
}