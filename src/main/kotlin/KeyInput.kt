import models.GameObjectAction
import models.GameObjectID
import models.entity.Player
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
        if (Game.gameState == State.Pause) {
            if (event.keyCode == KeyEvent.VK_ESCAPE) Game.gameState = State.Game
            SoundSystem.soundMap["menu-click"]!!.play()
            return
        }
        handler.gameObjects.forEach {
            if (it.id == GameObjectID.Player) {
                it as Player
                when (event.keyCode) {
                    KeyEvent.VK_W -> {
                        it.speedY = -5
                        wasd["W"] = true
                        it.action = GameObjectAction.Moving
                    }

                    KeyEvent.VK_S -> {
                        it.speedY = 5
                        wasd["S"] = true
                        it.action = GameObjectAction.Moving
                    }

                    KeyEvent.VK_A -> {
                        it.speedX = -5
                        wasd["A"] = true
                        it.action = GameObjectAction.Moving
                    }

                    KeyEvent.VK_D -> {
                        it.speedX = 5
                        wasd["D"] = true
                        it.action = GameObjectAction.Moving
                    }

                    KeyEvent.VK_ESCAPE -> {
                        Game.gameState = State.Pause
                        SoundSystem.soundMap["menu-click"]!!.play()
                    }

                    KeyEvent.VK_J -> {
                        it.action = GameObjectAction.Attack
                    }
                }
            }
        }
    }

    override fun keyReleased(event: KeyEvent) {
        if (Game.gameState == State.Pause) return
        handler.gameObjects.forEach {
            if (it.id == GameObjectID.Player) {
                it as Player
                when (event.keyCode) {
                    KeyEvent.VK_W -> {
                        wasd["W"] = false
                        if (wasd["S"] == true) it.speedY = 5
                        else {
                            it.speedY = 0
                            it.action = GameObjectAction.Idle
                        }
                    }

                    KeyEvent.VK_S -> {
                        wasd["S"] = false
                        if (wasd["W"] == true) it.speedY = -5
                        else {
                            it.speedY = 0
                            it.action = GameObjectAction.Idle
                        }
                    }

                    KeyEvent.VK_A -> {
                        wasd["A"] = false
                        if (wasd["D"] == true) it.speedX = 5
                        else {
                            it.speedX = 0
                            it.action = GameObjectAction.Idle
                        }
                    }

                    KeyEvent.VK_D -> {
                        wasd["D"] = false
                        if (wasd["A"] == true) it.speedX = -5
                        else {
                            it.speedX = 0
                            it.action = GameObjectAction.Idle
                        }
                    }

                    KeyEvent.VK_J -> {
                        if (wasd.any { moveInput -> moveInput.value }) {
                            it.action = GameObjectAction.Moving
                        } else {
                            it.action = GameObjectAction.Idle
                        }
                    }
                }
            }
        }
    }
}
