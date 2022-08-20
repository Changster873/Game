import models.entity.Player
import models.world.GameMap
import java.io.File
import java.util.*

/**
 * Game
 *
 * This object holds all information about the state of the game.
 * That includes data about the map, the player, and the game's progress itself.
 *
 */
class Game {
    private var state: MutableMap<String, String> = mutableMapOf()
    private var gameMap = GameMap()
    var player: Player? = null

    fun initialise() {
        println("Initialising game...")
        startNewGame("Chan")
        println("Loading map...")
        gameMap.loadMap("public/world-map/map_1.txt")
    }

    /**
     * Loads a saved game slot into the state controller
     */
    private fun loadGame() {
        println("Loading save file...")
        val file = File("/public/save_1.txt")
        runCatching {
            val scanner = Scanner(file)
            while (scanner.hasNext()) {
                val targetLine = scanner.nextLine()
                targetLine.split(":").let { state.put(it[0], it[1]) }
            }
        }.onFailure {
            println("Error: Could not find save file. Starting new game....")
        }
    }

    /**
     * Saves the current game state into a file
     */
    private fun saveGame(new: Boolean = false) {
        if (!new) println("Saving progress...")
        File("public/save_1.txt").bufferedWriter().use { out ->
            state.forEach {
                out.write("${it.key}:${it.value}\n")
            }
        }
    }

    /**
     * Starts a new game with the given player name
     */
    private fun startNewGame(name: String) {
        println("Starting new game...")
        val game = """
            Name:$name
            Level:1
            Map:public/world-map/map_1.txt
        """.trimIndent()
        val scanner = Scanner(game)
        while (scanner.hasNext()) {
            val targetLine = scanner.nextLine()
            targetLine.split(":").let {
                state.put(it[0], it[1])
            }
        }
        // same the state so a "Continue" button after the user has started a new game
        saveGame(true)
    }
}