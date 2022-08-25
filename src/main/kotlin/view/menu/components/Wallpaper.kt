package view.menu.components

import view.Screen
import java.awt.image.RescaleOp
import java.io.File
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JPanel

/**
 * The background wallpaper for the game's main menu.
 */
class Wallpaper : JPanel() {
    init {
        this.setBounds(0, -20, Screen.SCREEN_WIDTH, Screen.SCREEN_HEIGHT)
        var img = ImageIO.read(File("src/main/resources/assets/game-wallpaper.jpg"))
        val brightnessAdjust = RescaleOp(.8f, 0f, null)
        img = brightnessAdjust.filter(img, null)
        this.add(JLabel(ImageIcon(img)))
    }
}