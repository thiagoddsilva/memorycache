import io.github.memorycache.CacheItem
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

class CacheItemTest {

    @Test
    fun init_whenCalled_shouldReturnNewObjectWithData() {
        val cacheItem = CacheItem("key", "value", null)

        assertNotNull(cacheItem)
        assertEquals("key", cacheItem.key)
        assertEquals("value", cacheItem.value)
    }

    @Test
    fun init_whenCalledWith1SecondDuration_shouldReturnExpiredAfter1Second() {
        val cacheItem = CacheItem("key", "value", 1.seconds)

        Thread.sleep(1100)

        assertTrue(cacheItem.isExpired())
    }

    @Test
    fun init_whenCalledWith2SecondDuration_shouldReturnNotExpiredAfter1Second() {
        val cacheItem = CacheItem("key", "value", 2.seconds)

        Thread.sleep(1000)

        assertFalse(cacheItem.isExpired())
    }
}