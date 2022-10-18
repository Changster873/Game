package models

import java.awt.Graphics
import java.awt.Rectangle
import java.awt.image.BufferedImage

abstract class GameObject(
    var x: Int,
    var y: Int,
    var id: GameObjectID
) {
    // assets linked to this game object
    val animation: MutableMap<String, List<BufferedImage>> = mutableMapOf()

    // this will dictate how fast the object moves around the screen
    abstract var speedX: Int
    abstract var speedY: Int

    // instructions on what the game object needs to do the next frame
    abstract fun tick()

    // what the object will look like on screen
    abstract fun render(g: Graphics)

    // what space is taken up by this object on screen
    abstract fun getBounds(): Rectangle

    // determine whether this object has a collision or not
    abstract fun collision()

    companion object {
        /**
         * To limit the boundaries of objects moving on screen, cannot move past the min or max points of the frame.
         */
        fun clamp(variable: Int, min: Int, max: Int): Int {
            return if (variable >= max) {
                max
            } else if (variable <= min) {
                min
            } else variable
        }
    }
}

enum class GameObjectID {
    Player,
    BasicEnemy
}

enum class GameObjectAction {
    Idle,
    Moving,
    Attack
}
