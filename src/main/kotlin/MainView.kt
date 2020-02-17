import org.displee.CacheLibrary
import org.displee.CacheLibraryMode
import tornadofx.*
import java.io.File
import java.nio.file.Paths
import java.util.logging.Logger
import kotlin.system.exitProcess

class MainView : View("OSRS Cache Editor By Leviticus") {
    private var directory: File? = null
    private var library: CacheLibrary? = null
    private val configs = Paths.get("Configs")
    private val models = Paths.get("Models")
    private val maps = Paths.get("Maps")
    private val decodedItems = Paths.get("DecodedItems")
    private val decodedNpcs = Paths.get("DecodedNpcs")
    private val decodedObjects = Paths.get("DecodedObjects")
    private val logger: Logger = Logger.getLogger("Main")

    override val root = pane() {
        menubar() {
            menu("File") {
                item("Open").action {
                    directory = chooseDirectory("Select Target Directory"){}
                    library = CacheLibrary(directory?.absolutePath, CacheLibraryMode.UN_CACHED)//Uncached doesn't save data in the files of an archive

                    println(directory.toString())
                }
                item("Save","Shortcut+S").action {
                    hide()
                }
                item("Quit","Shortcut+Q").action {
                    exitProcess(0)
                }
            }
        }
    }
}
