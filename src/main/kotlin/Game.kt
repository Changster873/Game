import models.GameObjectID
import models.entity.BasicEnemy
import models.entity.Player
import models.world.GameMap
import view.HUD
import view.Screen
import view.Window
import java.awt.Canvas
import java.awt.Color
import java.io.File
import java.util.*

/** Main entry point of the game. */
fun main(args: Array<String>) {
    Game()
}

/**
 * Game
 *
 * This object holds all information about the state of the game.
 * That includes data about the map, the player, and the game's progress itself.
 *
 */
class Game : Runnable, Canvas() {
    private var thread: Thread? = null
    private var running = false
    private val handler: Handler = Handler()
    private val hud: HUD = HUD(handler)

    init {
        this.addKeyListener(KeyInput(handler))

        handler.addObject(Player(100.0, 100, 100, GameObjectID.Player, handler))
        handler.addObject(BasicEnemy(100.0, 200, 13, GameObjectID.BasicEnemy, handler))
        handler.addObject(BasicEnemy(100.0, 34, 213, GameObjectID.BasicEnemy, handler))
        handler.addObject(BasicEnemy(100.0, 234, 234, GameObjectID.BasicEnemy, handler))
        handler.addObject(BasicEnemy(100.0, 553, 543, GameObjectID.BasicEnemy, handler))
        handler.addObject(BasicEnemy(100.0, 1345, 23, GameObjectID.BasicEnemy, handler))
        handler.addObject(BasicEnemy(100.0, 234, 45, GameObjectID.BasicEnemy, handler))
        Window("Lost Game", this)
    }

    companion object {
        // Audio
        val music = SoundSystem()

        // Map
        var gameMap = GameMap()

        // Game logic
        var state: MutableMap<String, String> = mutableMapOf()
    }

    fun initialise() {
        gameMap.loadMap("src/main/resources/world-map/map_1.txt")
    }

    /**
     * Loads a saved game slot into the state controller
     */
    private fun loadGame() {
        State.assetLoadingState = "Loading save file"
        val file = File("src/main/resources/save_1.txt")
        runCatching {
            val scanner = Scanner(file)
            while (scanner.hasNext()) {
                val targetLine = scanner.nextLine()
                targetLine.split(":").let { state.put(it[0], it[1]) }
            }
        }.onFailure {
            State.assetLoadingState = "Error: Could not find save file. Starting new game."
        }
    }

    /**
     * Saves the current game state into a file
     */
    private fun saveGame(new: Boolean = false) {
        if (!new) State.assetLoadingState = "Saving progress"
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
        State.assetLoadingState = "Starting new game"
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

    @Synchronized
    fun start() {
        thread = Thread(this)
        thread!!.start()
        running = true
    }

    @Synchronized
    fun stop() {
        runCatching {
            running = false
            thread!!.join()
        }.onFailure { exception -> exception.printStackTrace() }
    }

    private fun tick() {
        handler.tick()
        hud.tick()
    }

    private fun render() {
        val bs = this.bufferStrategy
        if (bs == null) {
            this.createBufferStrategy(3)
            return
        }
        val g = bs.drawGraphics

        g.color = Color.BLACK
        g.fillRect(0, 0, Screen.SCREEN_WIDTH, Screen.SCREEN_HEIGHT)

        handler.render(g)
        hud.render(g)

        g.dispose()
        bs.show()
    }

    override fun run() {
        this.requestFocus()
        var lastTime = System.nanoTime()
        val amountOfTicks = 60.0
        val ns = 1000000000 / amountOfTicks
        var delta = 0.0
        var timer = System.currentTimeMillis()
        var frames = 0
        while (running) {
            val now = System.nanoTime()
            delta += (now - lastTime) / ns
            lastTime = now
            while (delta >= 1) {
                tick()
                delta--
            }
            if (running) {
                render()
            }
            frames++

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000
                println("FPS: $frames")
                frames = 0
            }
        }
        stop()
    }
}
