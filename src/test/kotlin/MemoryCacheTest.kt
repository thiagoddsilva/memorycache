import io.github.memorycache.MemoryCache
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

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
}