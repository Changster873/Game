package models.world

import view.MapCell
import java.io.File
import java.util.*

/**
 * GameMap
 *
 * Loads and retrieves the world map for the game.
 */
class GameMap {
    private var tiles: MutableList<MutableList<MapCell>> = mutableListOf()
    private var currentMap = ""

    /**
     * Loads given map, so the game can render the associated sprites
     */
    fun loadMap(whichMap: String) {
        State.assetLoadingState = "Loading world map"
        // clear the current map
        tiles = mutableListOf()
        currentMap = ""

        // populate new map
        val scanner = Scanner(File(whichMap))
        var yPos = 0
        while (scanner.hasNext()) {
            val targetLine = scanner.nextLine().toCharArray()
            val xRow = mutableListOf<MapCell>()
            targetLine.forEach { value ->
                xRow.add(MapCell.values().find { it.encoding == value.toString() }!!)
            }
            tiles.add(xRow)
            yPos++
        }
        // persist the location the player was last in so loading a save will get them back here
        currentMap = whichMap
    }
}
