import models.GameObjectID
import models.entity.BasicEnemy
import models.entity.Player
import models.world.GameMap
import view.HUD
import view.Screen
import view.Window
import view.menu.MainMenu
import view.sprites.SpritesLoader
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
    private val menu: MainMenu = MainMenu()
    private val audioSystem: SoundSystem = SoundSystem()
    private val spriteSheetLoader = SpritesLoader()

    init {
        audioSystem.load()

        spriteSheetLoader.getSpriteSheet("player", "player")

        this.addKeyListener(KeyInput(handler))
        this.addMouseListener(menu)

        Window("Lost Game", this)
    }

    companion object {
        var gameState: State = State.Menu

        // Map
        var gameMap = GameMap()

        // Game logic
        var state: MutableMap<String, String> = mutableMapOf()
    }

    fun initialise() {
        handler.addObject(Player(100.0, 100, 100, GameObjectID.Player, handler, spriteSheetLoader))
        handler.addObject(BasicEnemy(100.0, 200, 13, GameObjectID.BasicEnemy, handler))
        handler.addObject(BasicEnemy(100.0, 34, 213, GameObjectID.BasicEnemy, handler))
        handler.addObject(BasicEnemy(100.0, 234, 234, GameObjectID.BasicEnemy, handler))
        handler.addObject(BasicEnemy(100.0, 553, 543, GameObjectID.BasicEnemy, handler))
        handler.addObject(BasicEnemy(100.0, 1345, 23, GameObjectID.BasicEnemy, handler))
        handler.addObject(BasicEnemy(100.0, 234, 45, GameObjectID.BasicEnemy, handler))

        gameMap.loadMap("src/main/resources/world-map/map_1.txt")

        gameState = State.Game
    }

    /**
     * Loads a saved game slot into the state controller
     */
    private fun loadGame() {
        val file = File("src/main/resources/save_1.txt")
        runCatching {
            val scanner = Scanner(file)
            while (scanner.hasNext()) {
                val targetLine = scanner.nextLine()
                targetLine.split(":").let { state.put(it[0], it[1]) }
            }
        }.onFailure {
        }
    }

    /**
     * Saves the current game state into a file
     */
    private fun saveGame(new: Boolean = false) {
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
        if (gameState == State.Game) {
            hud.tick()
            handler.tick()
        }
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

        when (gameState) {
            State.Initialise -> {
                initialise()
            }

            State.Game -> {
                handler.render(g)
                hud.render(g)
            }

            State.Menu -> {
                menu.render(g)
            }

            State.Options -> {}
            State.Pause -> {}
            State.End -> {}
        }

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
