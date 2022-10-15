package view

import Game
import java.awt.Toolkit
import javax.swing.JFrame
import javax.swing.JPanel

/**
 * The game screen that will hold the gameplay frame.
 */
class Screen(game: Game) : JFrame() {
    init {
        this.initialise()
        // make frame appear
        this.isVisible = true
        // play sound for this screen
        Game.music.fileName = "src/main/resources/sound/adventure-music.mp3"
        Game.music.start()
    }

    /**
     * Changing the panel so the frame loads a new view.
     */
    private fun changePanel(panel: JPanel) {
        this.contentPane = panel
        // make frame appear
        this.revalidate()
        this.repaint()
        this.isVisible = true
    }

    companion object {
        val SCREEN_WIDTH = Toolkit.getDefaultToolkit().screenSize.width
        val SCREEN_HEIGHT = Toolkit.getDefaultToolkit().screenSize.height
    }
}

fun JFrame.initialise() {
    // 1080p resolution
    this.setBounds(0, 0, Screen.SCREEN_WIDTH, Screen.SCREEN_HEIGHT)
    // exit the program if user clicks on X
    this.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    this.isResizable = false
    this.setLocationRelativeTo(null)
    // full screen experience
    this.extendedState = JFrame.MAXIMIZED_BOTH
}

fun JPanel.initialise() {
    // 1080p resolution
    this.setBounds(0, 0, Screen.SCREEN_WIDTH, Screen.SCREEN_HEIGHT)
    // no layout
    this.layout = null
}
