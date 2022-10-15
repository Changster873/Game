package view

import Handler
import models.GameObjectID
import models.entity.Player
import java.awt.Color
import java.awt.Graphics

class HUD(
    private val handler: Handler
) {
    fun tick() {
        val player = handler.gameObjects.filter { it.id == GameObjectID.Player }[0] as Player
    }

    fun render(g: Graphics) {
        val player = handler.gameObjects.filter { it.id == GameObjectID.Player }[0] as Player
        g.color = Color.GRAY
        g.fillRect(15, 15, 200, 32)
        g.color = Color.GREEN
        g.fillRect(15, 15, (player.health * 2).toInt(), 32)
        g.color = Color.WHITE
        g.drawRect(15, 15, 200, 32)
    }
}
