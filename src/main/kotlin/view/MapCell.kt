package view

enum class MapCell(
    /**
     * The translation of the world map cell encoding. E.g. will hold MapCellEncoding.GRASS if value was G
     */
    val encoding: String,

    /**
     * Returns true if the rendered map cell allows an entity to occupy its space.
     */
    var habitable: Boolean = false,

    /**
     * Returns true if an entity is currently occupying the space.
     */
    var occupied: Boolean = false
) {
    GRASS("G", true, false);
}