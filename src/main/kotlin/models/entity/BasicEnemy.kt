package models.entity

import Handler
import models.GameObject
import models.GameObjectID
import models.properties.Destructible
import models.properties.Warmonger
import view.Screen
import java.awt.Color
import java.awt.Graphics
import java.awt.Rectangle
import java.awt.image.BufferedImage

class BasicEnemy(
    override var health: Double = 100.0,
    x: Int,
    y: Int,
    id: GameObjectID,
    private val handler: Handler,
    override var speedX: Int = 0,
    override var speedY: Int = 0
) : Destructible, Warmonger, GameObject(x, y, id) {
    var name: String? = null

    init {
        speedX = 5
        speedY = 5
    }

    override fun tick() {
        x += speedX
        y += speedY

        if (y <= 0 || y >= Screen.SCREEN_HEIGHT - 50) speedY *= -1
        if (x <= 0 || x >= Screen.SCREEN_WIDTH - 50) speedX *= -1
    }

    override fun render(g: Graphics) {
        g.color = Color.RED
        g.fillRect(x, y, 32, 32)
    }

    override fun getBounds(): Rectangle {
        return Rectangle(x, y, 32, 32)
    }

    override fun collision() {
        handler.gameObjects.forEach {
            if (it.id == GameObjectID.BasicEnemy) {
                if (getBounds().intersects((it as BasicEnemy).getBounds())) {
                    this.health -= 2
                }
            }
        }
    }
}
