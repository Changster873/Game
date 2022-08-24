package view

import javazoom.jl.player.Player
import view.menu.HomeScreen
import java.io.FileInputStream
import javax.swing.JFrame
import javax.swing.JPanel

/**
 * The game screen that will hold the gameplay frame.
 */
class Screen() : JFrame() {
    init {
        this.initialise()

        this.contentPane = HomeScreen()
        // make frame appear
        this.isVisible = true
        // play sound for this screen
        val music = FileInputStream("src/main/resources/sound/adventure-music.mp3")
        val player = Player(music)
        player.play()
    }

    companion object {
        const val SCREEN_WIDTH = 1920
        const val SCREEN_HEIGHT = 1080
    }
}

fun JFrame.initialise() {
    // 1080p resolution
    this.setBounds(0, 0, Screen.SCREEN_WIDTH, Screen.SCREEN_HEIGHT)
    // exit the program if user clicks on X
    this.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    // no layout
    this.layout = null
}

fun JPanel.initialise() {
    // 1080p resolution
    this.setBounds(0, 0, Screen.SCREEN_WIDTH, Screen.SCREEN_HEIGHT)
    // no layout
    this.layout = null
}

abstract class Callback {
    abstract fun invoke();
}