package models.entity

import Handler
import State
import models.GameObject
import models.GameObjectID
import models.properties.Destructible
import models.properties.Warmonger
import view.Screen
import view.sprites.FilePath
import view.sprites.Sprites
import java.awt.Color
import java.awt.Graphics
import java.awt.Rectangle
import java.awt.image.BufferedImage

class Player(
    override var health: Double = 100.0,
    x: Int,
    y: Int,
    id: GameObjectID,
    private val handler: Handler,
    override var speedX: Int = 0,
    override var speedY: Int = 0
) : Destructible, Warmonger, GameObject(x, y, id) {
    var name: String? = null
    var level: Int = 1
    var experience: Int = 0

    init {
        State.assetLoadingState = "Loading Character assets"
        animation["still"] = stillSprites()
        animation["move"] = movingSprites()
        animation["attack"] = attackSprites()
        animation["perish"] = perishedSprites()
    }

    override fun perishedSprites(): List<BufferedImage> {
        return Sprites.get(Sprites.spriteFileRange(listOf(24, 25, 26), FilePath.Player.path))
    }

    override fun stillSprites(): List<BufferedImage> {
        return Sprites.get(Sprites.spriteFileRange(listOf(1, 2, 3, 4, 5, 6), FilePath.Player.path))
    }

    override fun movingSprites(): List<BufferedImage> {
        return Sprites.get(Sprites.spriteFileRange(listOf(7, 8, 9, 10, 11, 12), FilePath.Player.path))
    }

    override fun attackSprites(): List<BufferedImage> {
        return Sprites.get(Sprites.spriteFileRange(listOf(13, 14, 15), FilePath.Player.path))
    }

    override fun tick() {
        x += speedX
        y += speedY

        x = clamp(x, 0, Screen.SCREEN_WIDTH - 100)
        y = clamp(y, 0, Screen.SCREEN_HEIGHT - 120)

        collision()
        handleExperience()
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

    override fun render(g: Graphics) {
        g.color = Color.WHITE
        g.fillRect(x, y, 32, 32)
    }

    override fun getBounds(): Rectangle {
        return Rectangle(x, y, 32, 32)
    }

    /**
     * Reset experience if max cap has been reached and also increase level.
     * Take leftover exp into account.
     */
    private fun handleExperience() {
        var leftOverExp = 0
        while (experience >= 100) {
            leftOverExp = (100 - experience) * -1
            level++
            experience = 0 + leftOverExp
        }
    }
}
