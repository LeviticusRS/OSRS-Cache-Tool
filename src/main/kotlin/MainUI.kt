import org.displee.CacheLibrary
import org.displee.CacheLibraryMode
import org.displee.io.impl.OutputStream
import java.awt.EventQueue
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import java.util.logging.Logger
import javax.swing.*


class MainUI(title: String) : JFrame() {
    private val directory = "repo/cache/"
    private var library = CacheLibrary(directory, CacheLibraryMode.UN_CACHED)//Uncached doesn't save data in the files of an archive
    private val configs = Paths.get("repo/Configs")
    private val models = Paths.get("repo/Models")
    private val maps = Paths.get("repo/Maps")
    private val decodedItems = Paths.get("repo/DecodedItems")
    private val decodedNpcs = Paths.get("repo/DecodedNpcs")
    private val decodedObjects = Paths.get("repo/DecodedObjects")
    private val logger: Logger = Logger.getLogger("Main")

    init {
        createUI(title)
    }

    private fun createUI(title: String) {
        setTitle(title)
        val packModels = JButton("Pack Models")
        val packMaps = JButton("Pack Maps")
        val packConfigs = JButton("Pack Configs")
        val encodeItems = JButton("Encode Items")
        val encodeObjects = JButton("Encode Objects")
        val encodeNpcs = JButton("Encode Npcs")
        val frame = JFrame()
        encodeObjects.addActionListener {
            if (createEncodedObjectsDefinition(decodedObjects)) {
                JOptionPane.showMessageDialog(frame, "Finished Encoding Pbjects")
            }
        }
        encodeItems.addActionListener {
            if (createEncodedItemsDefinition(decodedItems)) {
                JOptionPane.showMessageDialog(frame, "Finished Encoding Items")
            }
        }
        encodeNpcs.addActionListener {
            if (createEncodedNpcsDefinition(decodedNpcs)) {
                JOptionPane.showMessageDialog(frame, "Finished Encoding Npcs")
            }
        }
        packModels.addActionListener {
            if (packModels(models, library)) {
                JOptionPane.showMessageDialog(frame, "Finished Packing Models")
            }
        }
        packMaps.addActionListener {
            if (packMaps(maps, library)) {
                JOptionPane.showMessageDialog(frame, "Finished Packing Maps")
            }
        }
        packConfigs.addActionListener {
            if (packConfigs(configs, library)) {
                JOptionPane.showMessageDialog(frame, "Finished Packing Configs")
            }
        }
        createLayout(packMaps, packModels, packConfigs, encodeNpcs, encodeItems, encodeObjects)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setSize(700, 100)
        isResizable = false
        setLocationRelativeTo(null)
    }

    private fun createLayout(vararg arg: JComponent) {

        val gl = GroupLayout(contentPane)
        contentPane.layout = gl

        gl.autoCreateContainerGaps = true
        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addComponent(arg[0])
                .addComponent(arg[1])
                .addComponent(arg[2])
                .addComponent(arg[3])
                .addComponent(arg[4])
                .addComponent(arg[5])
        )

        gl.setVerticalGroup(gl.createParallelGroup()
                .addComponent(arg[0])
                .addComponent(arg[1])
                .addComponent(arg[2])
                .addComponent(arg[3])
                .addComponent(arg[4])
                .addComponent(arg[5])
        )

        pack()
    }

    public fun createEncodedItemsDefinition(path: Path): Boolean {
        for (itemDef in Files.walk(path, 1)) {
            if (itemDef == path) {
                continue
            }
            val id = itemDef.fileName.toString().replace(".txt", "").toInt()
            val buffer = OutputStream(1000)
            //Open the file and pass it to a Scanner object.
            Scanner(FileReader(itemDef.toFile())).use { sc ->
                //Loop until we get to the end of the file
                while (sc.hasNext()) {

                    //Grab the next word
                    val word = sc.next()
                    //Test that it's not a blank string
                    if (word.isNotBlank()) {
                        val list: List<String> = word.split("=")
                        val opcode = list[0]
                        val value = list[1]
                        when (opcode) {
                            "imodel" -> {
                                buffer.writeByte(1)
                                buffer.writeShort(value.toInt())
                            }
                            "name" -> {
                                buffer.writeByte(2)
                                buffer.writeString(value.replace("_", " "))
                            }
                            "maleModel" -> {
                                buffer.writeByte(23)
                                buffer.writeShort(value.toInt())
                                buffer.writeByte(0)
                            }
                            "femaleModel" -> {
                                buffer.writeByte(25)
                                buffer.writeShort(value.toInt())
                                buffer.writeByte(0)
                            }
                            "options1" -> {
                                buffer.writeByte(30)
                                buffer.writeString(value)
                            }
                            "options2" -> {
                                buffer.writeByte(31)
                                buffer.writeString(value)
                            }
                            "options3" -> {
                                buffer.writeByte(32)
                                buffer.writeString(value)
                            }
                            "options4" -> {
                                buffer.writeByte(33)
                                buffer.writeString(value)
                            }
                            "options5" -> {
                                buffer.writeByte(34)
                                buffer.writeString(value)
                            }
                            "ioptions1" -> {
                                buffer.writeByte(35)
                                buffer.writeString(value)
                            }
                            "ioptions2" -> {
                                buffer.writeByte(36)
                                buffer.writeString(value)
                            }
                            "ioptions3" -> {
                                buffer.writeByte(37)
                                buffer.writeString(value)
                            }
                            "ioptions4" -> {
                                buffer.writeByte(38)
                                buffer.writeString(value)
                            }
                            "ioptions5" -> {
                                buffer.writeByte(39)
                                buffer.writeString(value)
                            }
                            "stackable" -> {
                                buffer.writeByte(11)
                            }
                            "ge" -> {
                                buffer.writeByte(65)
                            }
                            "cost" -> {
                                buffer.writeByte(12)
                                buffer.writeInt(value.toInt())
                            }
                            "resizex" -> {
                                buffer.writeByte(110)
                                buffer.writeShort(value.toInt())
                            }
                            "resizey" -> {
                                buffer.writeByte(111)
                                buffer.writeShort(value.toInt())
                            }
                            "resizez" -> {
                                buffer.writeByte(112)
                                buffer.writeShort(value.toInt())
                            }
                            "ambient" -> {
                                buffer.writeByte(113)
                                buffer.writeByte(value.toInt())
                            }
                            "contrast" -> {
                                buffer.writeByte(114)
                                buffer.writeByte(value.toInt())
                            }
                            "zan2d" -> {
                                buffer.writeByte(95)
                                buffer.writeShort(value.toInt())
                            }
                            "zoom2d" -> {
                                buffer.writeByte(4)
                                buffer.writeShort(value.toInt())
                            }
                            "xan2d" -> {
                                buffer.writeByte(5)
                                buffer.writeShort(value.toInt())
                            }
                            "yan2d" -> {
                                buffer.writeByte(6)
                                buffer.writeShort(value.toInt())
                            }
                            "xof2d" -> {
                                buffer.writeByte(7)
                                buffer.writeShort(value.toInt())
                            }
                            "yof2d" -> {
                                buffer.writeByte(8)
                                buffer.writeShort(value.toInt())
                            }
                            "notelink" -> {
                                buffer.writeByte(97)
                                buffer.writeShort(value.toInt())
                            }
                            "noteModel" -> {
                                buffer.writeByte(98)
                                buffer.writeShort(value.toInt())
                            }
                        }
                    }
                }
            }
            val outputPath = Paths.get("$configs/items/$id")
            if (Files.notExists(outputPath)) {
                Files.createFile(outputPath).toFile()
            }
            buffer.writeByte(0)
            val toWrite = buffer.flip()
            Files.write(outputPath, toWrite)
            logger.info("Finished encoding $id")
        }
        logger.info("Finished encoding item definitions.")
        return true
    }

    public fun createEncodedNpcsDefinition(path: Path): Boolean {
        for (npcDef in Files.walk(path, 1)) {
            if (npcDef == path) {
                continue
            }
            val id = npcDef.fileName.toString().replace(".txt", "").toInt()
            val buffer = OutputStream(1000)
            //Open the file and pass it to a Scanner object.
            Scanner(FileReader(npcDef.toFile())).use { sc ->
                //Loop until we get to the end of the file
                while (sc.hasNext()) {

                    //Grab the next word
                    val word = sc.next()
                    //Test that it's not a blank string
                    if (word.isNotBlank()) {
                        val list: List<String> = word.split("=")
                        val opcode = list[0]
                        val value = list[1]
                        when (opcode) {
                            "model" -> {
                                buffer.writeByte(1)
                                buffer.writeByte(1)
                                buffer.writeShort(value.toInt())
                            }
                            "name" -> {
                                buffer.writeByte(2)
                                buffer.writeString(value.replace("_", " "))
                            }
                            "combatLevel" -> {
                                buffer.writeByte(95)
                                buffer.writeShort(value.toInt())
                            }
                            "width" -> {
                                buffer.writeByte(97)
                                buffer.writeShort(value.toInt())
                            }
                            "height" -> {
                                buffer.writeByte(98)
                                buffer.writeShort(value.toInt())
                            }
                            "size" -> {
                                buffer.writeByte(12)
                                buffer.writeByte(value.toInt())
                            }
                            "stand" -> {
                                buffer.writeByte(13)
                                buffer.writeShort(value.toInt())
                            }
                            "walk" -> {
                                buffer.writeByte(15)
                                buffer.writeShort(value.toInt())
                            }
                            "options1" -> {
                                buffer.writeByte(30)
                                buffer.writeString(value)
                            }
                            "options2" -> {
                                buffer.writeByte(31)
                                buffer.writeString(value)
                            }
                            "options3" -> {
                                buffer.writeByte(32)
                                buffer.writeString(value)
                            }
                            "options4" -> {
                                buffer.writeByte(33)
                                buffer.writeString(value)
                            }
                            "options5" -> {
                                buffer.writeByte(34)
                                buffer.writeString(value)
                            }
                        }
                    }
                }
            }
            val outputPath = Paths.get("$configs/npcs/$id")
            if (Files.notExists(outputPath)) {
                Files.createFile(outputPath).toFile()
            }
            buffer.writeByte(0)
            val toWrite = buffer.flip()
            Files.write(outputPath, toWrite)
            logger.info("Finished encoding $id")
        }
        logger.info("Finished encoding npc definitions.")
        return true
    }
    public fun createEncodedObjectsDefinition(path: Path): Boolean {
        for (objectDef in Files.walk(path, 1)) {
            if (objectDef == path) {
                continue
            }
            val id = objectDef.fileName.toString().replace(".txt", "").toInt()
            val buffer = OutputStream(1000)
            //Open the file and pass it to a Scanner object.
            Scanner(FileReader(objectDef.toFile())).use { sc ->
                //Loop until we get to the end of the file
                while (sc.hasNext()) {

                    //Grab the next word
                    var word = sc.next()
                    //Test that it's not a blank string
                    if (word.isNotBlank()) {
                        var list: List<String> = word.split("=")
                        var opcode = list[0]
                        var value = list[1]
                        when (opcode) {
                            "objectModelsWithTypes" -> {
                                buffer.writeByte(1)
                                buffer.writeByte(1)
                                buffer.writeShort(value.toInt())
                                word = sc.next()
                                list = word.split("=")
                                opcode = list[0]
                                value = list[1]
                                if (opcode == "objectTypes") {
                                    buffer.writeByte(value.toInt())
                                }
                            }
                            "name" -> {
                                buffer.writeByte(2)
                                buffer.writeString(value.replace("_", " "))
                            }
                            "objectModels" -> {
                                buffer.writeByte(5)
                                buffer.writeByte(1)
                                buffer.writeShort(value.toInt())
                            }
                            "sizeX" -> {
                                buffer.writeByte(14)
                                buffer.writeByte(value.toInt())
                            }
                            "sizeY" -> {
                                buffer.writeByte(15)
                                buffer.writeByte(value.toInt())
                            }
                            "clipType" -> {
                                buffer.writeByte(17)
                            }
                            "intop19" -> {
                                buffer.writeByte(19)
                                buffer.writeByte(value.toInt())
                            }
                            "animationId" -> {
                                buffer.writeByte(24)
                                buffer.writeShort(value.toInt())
                            }
                            "clipType1" -> {
                                buffer.writeByte(27)
                            }
                            "ambient" -> {
                                buffer.writeByte(29)
                                buffer.writeByte(value.toInt())
                            }
                            "options1" -> {
                                if (value != "null") {
                                    buffer.writeByte(30)
                                    buffer.writeString(value)
                                }
                            }
                            "options2" -> {
                                if (value != "null") {
                                    buffer.writeByte(31)
                                    buffer.writeString(value)
                                }
                            }
                            "options3" -> {
                                if (value != "null") {
                                    buffer.writeByte(32)
                                    buffer.writeString(value)
                                }
                            }
                            "options4" -> {
                                if (value != "null") {
                                    buffer.writeByte(33)
                                    buffer.writeString(value)
                                }
                            }
                            "options5" -> {
                                if (value != "null") {
                                    buffer.writeByte(34)
                                    buffer.writeString(value)
                                }
                            }
                            "contrast" -> {
                                buffer.writeByte(39)
                                buffer.writeByte(value.toInt())
                            }
                            "recolorToFind" -> {
                                buffer.writeByte(40)
                                buffer.writeByte(1)
                                buffer.writeShort(value.toInt())
                                word = sc.next()
                                list = word.split("=")
                                opcode = list[0]
                                value = list[1]
                                if (opcode == "recolorToReplace") {
                                    buffer.writeShort(value.toInt())
                                }
                            }
                            "intop78" -> {
                                buffer.writeByte(78)
                                buffer.writeShort(value.toInt())
                                word = sc.next()
                                list = word.split("=")
                                opcode = list[0]
                                value = list[1]
                                if (opcode == "intop78and79") {
                                    buffer.writeByte(value.toInt())
                                }
                            }
                            "intop75" -> {
                                buffer.writeByte(75)
                                buffer.writeByte(value.toInt())
                            }
                        }
                    }
                }
            }
            val outputPath = Paths.get("$configs/objects/$id")
            if (Files.notExists(outputPath)) {
                Files.createFile(outputPath).toFile()
            }
            buffer.writeByte(0)
            val toWrite = buffer.flip()
            Files.write(outputPath, toWrite)
            logger.info("Finished encoding $id")
        }
        logger.info("Finished encoding object definitions.")
        return true
    }

    public fun packConfigs(path: Path, cache: CacheLibrary): Boolean {
        val configs = cache.getIndex(2)

        for (configFolder in Files.walk(path, 1)) {
            if (configFolder == path) {
                continue
            }

            val configGroup = JS5ConfigGroup.forName(configFolder.fileName.toString())
                    ?: error("Unable to find JS5ConfigGroup for \"${configFolder.fileName}\".")

            val group = configs.getArchive(configGroup.group)
            for (config in Files.walk(configFolder, 1)) {
                if (config == configFolder) {
                    continue
                }

                val id = config.fileName.toString().toInt()
                val buffer = Files.readAllBytes(config)

                group.addFile(id, buffer)
                logger.info("Added ${configGroup.name.toLowerCase()} $id.")
            }

        }
        if (configs.update()) {
            logger.info("Finished packing item configs.")
        }
        return true
    }

    public fun packModels(path: Path, cache: CacheLibrary): Boolean {
        val models = cache.getIndex(7)
        for (model in Files.walk(path, 1)) {
            if (model == path) {
                continue
            }

            val id = model.fileName.toString().replace(".dat", "").toInt()
            val buffer = Files.readAllBytes(model)
            models.addArchive(id).addFile(0, buffer)
            logger.info("Added $id.")
        }
        if (models.update()) {
            logger.info("Finished packing")
        }
        return true
    }

    public fun packMaps(path: Path, cache: CacheLibrary): Boolean {
        val maps = cache.getIndex(5)
        for (map in Files.walk(path, 1)) {
            if (map == path) {
                continue
            }

            val fileName = map.fileName.toString().replace(".dat", "")
            val id = fileName.toInt()

            val buffer = Files.readAllBytes(map)
            //val region = if (id == 2140 || id == 2141) 7990 else 7991
            //val ending = "${region shr 8 and 0xFF}_${region and 0xFF}"
            //val archiveName = if (objects) "m$ending" else if (landscape) "l$ending" else ""
            maps.getArchive(id, true).addFile(0, buffer, 0)
            logger.info("Added $id")

        }

        if (maps.update()) {
            logger.info("Finished packing")
        }

        return true
    }
}

private fun createAndShowGUI() {

    val frame = MainUI("OSRS Cache Tool")
    frame.isVisible = true
}

fun main(args: Array<String>) {
    EventQueue.invokeLater(::createAndShowGUI)
}
