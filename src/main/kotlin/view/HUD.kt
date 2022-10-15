package view

import Handler
import models.GameObject
import models.GameObjectID
import models.entity.Player
import java.awt.Color
import java.awt.Graphics

class HUD(
    private val handler: Handler
) {
    private var green = 255

    fun tick() {
        val player = handler.gameObjects.filter { it.id == GameObjectID.Player }[0] as Player
        player.health = GameObject.clamp(player.health.toInt(), 0, 100).toDouble()

        // this will make the HUD change color as our health decreases
        green = GameObject.clamp(green, 0, 255)
        green = ((player.health * 255) / 100).toInt()
    }

    fun render(g: Graphics) {
        val player = handler.gameObjects.filter { it.id == GameObjectID.Player }[0] as Player
        // health
        g.color = Color.GRAY
        g.fillRect(15, 15, 200, 32)
        g.color = Color(75, green, 0)
        g.fillRect(15, 15, (player.health * 2).toInt(), 32)
        g.color = Color.WHITE
        g.drawRect(15, 15, 200, 32)
        // experience
        g.color = Color.GRAY
        g.fillRect(116, 49, 100, 10)
        g.color = Color.CYAN
        g.fillRect(116, 49, player.experience, 10)
        // score and level
        g.color = Color.WHITE
        g.drawString("Level: ${player.level}", 15, 64)
    }
}
