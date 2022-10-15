package view

import Game
import java.awt.Canvas
import javax.swing.JFrame

/**
 * Main game frame.
 */
class Window(title: String, game: Game) : Canvas() {
    init {
        val frame = JFrame(title)
        frame.initialise()
        frame.add(game)
        frame.isVisible = true
        game.start()
    }
}
