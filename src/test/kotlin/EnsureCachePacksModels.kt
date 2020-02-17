import org.displee.CacheLibrary
import org.displee.CacheLibraryMode
import org.junit.Test
import java.nio.file.Paths
import javax.swing.JOptionPane


class EnsureCachePacksModels {

    private val directory = "repo/cache/"
    private var library = CacheLibrary(directory, CacheLibraryMode.UN_CACHED)
    private val models = Paths.get("repo/Models")
    private val frame = MainUI("OSRS Cache Tool")


    @Test
    fun start() {
        //Don't show the java frame
        frame.isVisible = false

        assert(frame.packModels(models, library)) { "Cache could not pack models in repo" }
    }
}