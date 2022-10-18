package models.entity

import Handler
import SoundSystem
import models.GameObject
import models.GameObjectAction
import models.GameObjectID
import models.properties.Destructible
import models.properties.Warmonger
import view.Screen
import view.sprites.Sprites
import view.sprites.SpritesLoader
import java.awt.Color
import java.awt.Graphics
import java.awt.Rectangle

class Player(
    override var health: Double = 100.0,
    x: Int,
    y: Int,
    id: GameObjectID,
    private val handler: Handler,
    val spritesLoader: SpritesLoader,
    override var speedX: Int = 0,
    override var speedY: Int = 0
) : Destructible, Warmonger, GameObject(x, y, id) {
    var name: String? = null
    var level: Int = 1
    var experience: Int = 0
    var iteration = 10
    var spriteMaxLimit = 60
    var action = GameObjectAction.Idle

    override fun tick() {
        x += speedX
        y += speedY

        spriteMaxLimit = when (action) {
            GameObjectAction.Idle -> {
                60
            }

            GameObjectAction.Moving -> {
                60
            }

            GameObjectAction.Attack -> {
                40
            }
        }

        if (iteration <= spriteMaxLimit) {
            iteration++
        } else iteration = 10

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
                    SoundSystem.soundMap["collision"]!!.play()
                }
            }
        }
    }

    override fun render(g: Graphics) {
        g.color = Color.WHITE
        g.drawImage(
            Sprites.grabFromSpriteSheet(action.ordinal + 1, iteration.floorDiv(10), 48, 48, spritesLoader.sprites["player"]!!),
            x,
            y,
            200,
            200,
            null
        )
        g.drawRect(x + 75, y + 95, 55, 85)
    }

    override fun getBounds(): Rectangle {
        return Rectangle(x + 75, y + 95, 55, 85)
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
