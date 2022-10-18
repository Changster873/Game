package view.menu

import Callback
import java.awt.Graphics
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.sound.sampled.AudioSystem

data class ButtonProps(
    val x: Int,
    val y: Int,
    val name: String,
    val callback: Callback
)

abstract class Menu(
    val listOfButtons: List<ButtonProps>
) : MouseAdapter() {
    companion object {
        const val buttonWidth = 300
        const val buttonHeight = 100
        const val buttonX = 810
        const val buttonY = 400
        const val buttonGap = 110
    }

    protected var mouseX: Int? = null
    protected var mouseY: Int? = null

    override fun mousePressed(e: MouseEvent) {
        val mx = e.x
        val my = e.y
        listOfButtons.forEachIndexed { index, value ->
            if (mouseOver(mx, my, buttonX, buttonY + buttonGap * index)) {
                value.callback.invoke()
                SoundSystem.soundMap["menu-click"]!!.play()
            }
        }
    }

    override fun mouseReleased(e: MouseEvent) {
    }

    private fun mouseOver(mx: Int, my: Int, x: Int, y: Int): Boolean {
        if (mx > x && mx < x + buttonWidth) {
            if (my > y && my < y + buttonHeight) {
                return true
            }
            return false
        }
        return false
    }

    abstract fun render(g: Graphics)
    fun tick() {
    }
}
