package view.menu.components

import java.awt.Color
import java.awt.Cursor
import java.awt.Font
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JLabel
import javax.swing.SwingConstants

/**
 * Renders a button for the main menu at x and y coordinates with given text.
 *
 * If there is a callback method provided, that button will invoke that call upon click.
 *
 */
class MenuButton(x: Int, y: Int, text: String, buttonAction: Callback?) : JLabel() {
    init {
        this.setBounds(x, y, 500, 100)

        this.background = Color(230, 230, 230)
        this.foreground = Color(64, 64, 64)
        this.font = Font("Chiller", Font.PLAIN, 72)
        this.isOpaque = true

        this.text = text
        this.horizontalAlignment = SwingConstants.CENTER

        this.addMouseListener(object : MouseAdapter() {
            override fun mouseEntered(e: MouseEvent?) {
                background = Color(200, 200, 200)
                foreground = Color(180, 0, 0)
                cursor = Cursor(Cursor.HAND_CURSOR)
            }

            override fun mouseExited(e: MouseEvent?) {
                background = Color(230, 230, 230)
                foreground = Color(64, 64, 64)
            }

            override fun mouseClicked(e: MouseEvent?) {
                buttonAction?.invoke()
            }
        })
    }
}

/**
 * The callback function that is run after a set of events.
 */
abstract class Callback {
    abstract fun invoke();
}