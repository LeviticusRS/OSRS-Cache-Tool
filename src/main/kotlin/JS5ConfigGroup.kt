enum class JS5ConfigGroup(val group: Int) {
    OBJECTS(6),
    ENUM(8),
    NPCS(9),
    ITEMS(10);

    companion object {

        private val VALUES = values()

        private val NAME_TO_GROUP = VALUES.associateBy { it.name.toLowerCase() }

        fun forName(name: String): JS5ConfigGroup? {
            return NAME_TO_GROUP[name]
        }

    }

}