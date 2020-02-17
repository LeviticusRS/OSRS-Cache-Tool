import org.displee.CacheLibrary
import org.displee.CacheLibraryMode
import org.junit.Test
import java.nio.file.Paths


class EnsureCachePacksConfigs {

    private val directory = "repo/cache/"
    private var library = CacheLibrary(directory, CacheLibraryMode.UN_CACHED)
    private val configs = Paths.get("repo/Configs")
    private val frame = MainUI("OSRS Cache Tool")


    @Test
    fun start() {
        //Don't show the java frame
        frame.isVisible = false

        assert(frame.packConfigs(configs, library)) { "Cache could not pack configs in repo" }
    }
}