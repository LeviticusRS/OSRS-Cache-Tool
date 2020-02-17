import org.displee.CacheLibrary
import org.displee.CacheLibraryMode
import org.junit.Test


class EnsureCacheCloses {

    private val directory = "repo/cache/"
    private var library = CacheLibrary(directory, CacheLibraryMode.UN_CACHED)

    @Test
    fun start() {
        library.close()
        assert(library.isClosed) { "Cache has not closed" }
    }
}