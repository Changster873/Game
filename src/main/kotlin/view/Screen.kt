package view

import java.awt.Toolkit
import javax.swing.JFrame
import javax.swing.JPanel

/**
 * The game screen that will hold the gameplay frame.
 */
class Screen {
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
    this.isResizable = true
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
