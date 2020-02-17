import org.displee.CacheLibrary
import org.displee.CacheLibraryMode
import org.junit.Test


class EnsureCacheIsOSRSTest {

    private val directory = "repo/cache/"
    private var library = CacheLibrary(directory, CacheLibraryMode.UN_CACHED)

    @Test
    fun start() {
        assert(library.isOSRS) { "Cache is not an OSRS Cache" }
    }
}