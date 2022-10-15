import models.GameObjectID
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

/**
 * A key event listener, users will provide the game input and the game will act accordingly.
 */
class KeyInput(
    private val handler: Handler
) : KeyAdapter() {
    private val wasd = mutableMapOf<String, Boolean>()

    init {
        listOf("W", "A", "S", "D").forEach {
            wasd[it] = false
        }
    }

    override fun keyPressed(event: KeyEvent) {
        handler.gameObjects.forEach {
            if (it.id == GameObjectID.Player) {
                when (event.keyCode) {
                    KeyEvent.VK_W -> {
                        it.speedY = -5
                        wasd["W"] = true
                    }
                    KeyEvent.VK_S -> {
                        it.speedY = 5
                        wasd["S"] = true
                    }
                    KeyEvent.VK_A -> {
                        it.speedX = -5
                        wasd["A"] = true
                    }
                    KeyEvent.VK_D -> {
                        it.speedX = 5
                        wasd["D"] = true
                    }
                }
            }
        }
    }

    override fun keyReleased(event: KeyEvent) {
        handler.gameObjects.forEach {
            if (it.id == GameObjectID.Player) {
                when (event.keyCode) {
                    KeyEvent.VK_W -> {
                        wasd["W"] = false
                        if (wasd["S"] == true) it.speedY = 5
                        else it.speedY = 0
                    }
                    KeyEvent.VK_S -> {
                        wasd["S"] = false
                        if (wasd["W"] == true) it.speedY = -5
                        else it.speedY = 0
                    }
                    KeyEvent.VK_A -> {
                        wasd["A"] = false
                        if (wasd["D"] == true) it.speedX = 5
                        else it.speedX = 0
                    }
                    KeyEvent.VK_D -> {
                        wasd["D"] = false
                        if (wasd["A"] == true) it.speedX = -5
                        else it.speedX = 0
                    }
                }
            }
        }
    }
}
