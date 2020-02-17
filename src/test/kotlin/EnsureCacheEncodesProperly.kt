import org.junit.Test
import java.nio.file.Paths


class EnsureCacheEncodesProperly {
    private val decodedItems = Paths.get("repo/DecodedItems")
    private val decodedNpcs = Paths.get("repo/DecodedNpcs")
    private val decodedObjects = Paths.get("repo/DecodedObjects")
    private val frame = MainUI("OSRS Cache Tool")


    @Test
    fun start() {
        //Don't show the java frame
        frame.isVisible = false

        assert(frame.createEncodedObjectsDefinition(decodedObjects)) { "Cache could not encode object definitions in repo" }
        assert(frame.createEncodedNpcsDefinition(decodedNpcs)) { "Cache could not encode npcs definitions in repo" }
        assert(frame.createEncodedItemsDefinition(decodedItems)) { "Cache could not encode items definitions in repo" }

    }
}