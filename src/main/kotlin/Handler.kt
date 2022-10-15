import models.GameObject
import java.awt.Graphics
import java.util.LinkedList

/**
 * A handler that updates all game states every frame.
 */
class Handler {
    val gameObjects = LinkedList<GameObject>()

    fun tick() {
        gameObjects.forEach {
            it.tick()
        }
    }

    fun render(g: Graphics) {
        gameObjects.forEach {
            it.render(g)
        }
    }

    fun addObject(gameObj: GameObject) {
        gameObjects.add(gameObj)
    }

    fun removeObject(gameObj: GameObject) {
        gameObjects.remove(gameObj)
    }
}
