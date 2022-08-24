package view.menu

import view.Callback
import view.initialise
import view.menu.components.MenuButton
import view.menu.components.Wallpaper
import java.awt.Font
import java.io.File
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingConstants
import kotlin.system.exitProcess


class HomeScreen : JPanel() {
    init {
        this.initialise()

        // title of the game
        val title = JLabel("LOST", SwingConstants.CENTER)
        title.font = Font("Courier New", Font.PLAIN, 125)
        title.setBounds(350, 10, 300, 300)
        this.add(title)
        val bloodSplatter = ImageIO.read(File("src/main/resources/assets/blood-splatter.png"))
        val bloodIcon = JLabel(ImageIcon(bloodSplatter))
        bloodIcon.setBounds(360, -40, 300, 300)
        this.add(bloodIcon)

        // start button
        this.add(MenuButton(250, 350, "Start Game", null))

        // exit button
        this.add(MenuButton(250, 650, "Exit Game", object : Callback() {
            override fun invoke() {
                exitProcess(0)
            }
        }))

        // background image
        val background = Wallpaper()
        this.add(background)

        this.isVisible = true
    }
}