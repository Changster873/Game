package view.menu

import Callback
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import kotlin.system.exitProcess

val listOfButtons = listOf(
    ButtonProps(
        875,
        463,
        "Start Game",
        object : Callback {
            override fun invoke() {
                Game.gameState = State.Initialise
            }
        }
    ),
    ButtonProps(
        905,
        573,
        "Options",
        object : Callback {
            override fun invoke() {
                Game.gameState = State.Options
            }
        }
    ),
    ButtonProps(
        925,
        685,
        "Exit",
        object : Callback {
            override fun invoke() {
                exitProcess(1)
            }
        }
    )
)

class MainMenu : Menu(listOfButtons) {
    override fun render(g: Graphics) {
        val font = Font("Chiller", 1, 50)

        listOfButtons.forEachIndexed { index, value ->
            g.color = Color.WHITE
            g.drawRect(buttonX, buttonY + buttonGap * index, buttonWidth, buttonHeight)
            // TODO: Mouse hover changes color of menu entry
//            runCatching {
//                if (mouseOver(mouseX!!, mouseY!!, buttonX, buttonY + buttonGap * index)) {
//                    g.color = Color.RED
//                }
//            }.onFailure {
//                g.color = Color.WHITE
//            }
            g.font = font
            g.drawString(value.name, value.x, value.y)
        }
    }
}
