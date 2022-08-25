import models.entity.Player
import models.world.GameMap
import view.Screen
import java.io.File
import java.util.*

class State {
    companion object {
        var game: Game? = null
        var assetLoadingState: String = ""
    }
}

/**
 * Game
 *
 * This object holds all information about the state of the game.
 * That includes data about the map, the player, and the game's progress itself.
 *
 */
class Game {
    // Visual
    var screen: Screen? = null

    companion object {
        // Audio
        val music = SoundSystem()

        // Map
        var gameMap = GameMap()

        // Game logic
        var state: MutableMap<String, String> = mutableMapOf()
        var player: Player? = Player()
    }

    fun initialise() {
        gameMap.loadMap("src/main/resources/world-map/map_1.txt")
    }

    /**
     * Loads a saved game slot into the state controller
     */
    private fun loadGame() {
        State.assetLoadingState = "Loading save file..."
        val file = File("src/main/resources/save_1.txt")
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
        if (!new) State.assetLoadingState = "Saving progress..."
        File("src/main/resources/save_1.txt").bufferedWriter().use { out ->
            state.forEach {
                out.write("${it.key}:${it.value}\n")
            }
        }
    }

    /**
     * Starts a new game with the given player name
     */
    fun startNewGame(name: String) {
        State.assetLoadingState = "Starting new game..."
        val game = """
            Name:$name
            Level:1
            Map:src/main/resources/world-map/map_1.txt
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