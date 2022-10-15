import models.GameObjectID
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

/**
 * A key event listener, users will provide the game input and the game will act accordingly.
 */
class KeyInput(
    private val handler: Handler
) : KeyAdapter() {
    override fun keyPressed(event: KeyEvent) {
        handler.gameObjects.forEach {
            if (it.id == GameObjectID.Player) {
                when (event.keyCode) {
                    KeyEvent.VK_W -> it.speedY = -5
                    KeyEvent.VK_S -> it.speedY = 5
                    KeyEvent.VK_A -> it.speedX = -5
                    KeyEvent.VK_D -> it.speedX = 5
                }
            }
        }
    }

    override fun keyReleased(event: KeyEvent) {
        handler.gameObjects.forEach {
            if (it.id == GameObjectID.Player) {
                when (event.keyCode) {
                    KeyEvent.VK_W -> it.speedY = 0
                    KeyEvent.VK_S -> it.speedY = 0
                    KeyEvent.VK_A -> it.speedX = 0
                    KeyEvent.VK_D -> it.speedX = 0
                }
            }
        }
    }
}
