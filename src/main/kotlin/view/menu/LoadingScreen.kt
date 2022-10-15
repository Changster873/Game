package view.menu

import Game
import view.initialise
import java.awt.Color
import java.awt.Font
import java.util.*
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingConstants

/**
 * The loading screen when transitioning between two views.
 */
class LoadingScreen(game: Game) : JPanel() {
    private val timer = Timer()

    init {
        this.initialise()

        // loading dots, will go from 0 to 3 dots and reset
        var dots = ""

        // loading text
        val loading = JLabel("Loading", SwingConstants.LEFT)
        loading.foreground = Color(230, 230, 230)
        loading.font = Font("Courier New", Font.PLAIN, 72)
        loading.setBounds(1300, 550, 500, 500)
        this.add(loading)

        // incremental dots
        val dotsOnScreen = JLabel(dots, SwingConstants.LEFT)
        dotsOnScreen.foreground = Color(230, 230, 230)
        dotsOnScreen.font = Font("Courier New", Font.PLAIN, 72)
        dotsOnScreen.setBounds(1600, 550, 500, 500)
        this.add(dotsOnScreen)

        // what is currently processing while the game is loading
        val assetLoadingState = JLabel(State.assetLoadingState, SwingConstants.LEFT)
        assetLoadingState.foreground = Color(230, 230, 230)
        assetLoadingState.font = Font("Courier New", Font.PLAIN, 18)
        assetLoadingState.setBounds(1400, 600, 500, 500)
        this.add(assetLoadingState)

        this.add(game)

        // loading dots set
        timer.scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    if (dots == "...") {
                        dots = ""
                    }
                    dots += "."
                    dotsOnScreen.text = dots
                    dotsOnScreen.revalidate()
                    dotsOnScreen.repaint()

                    assetLoadingState.text = State.assetLoadingState
                    assetLoadingState.revalidate()
                    assetLoadingState.repaint()
                }
            },
            500,
            1000
        )

        this.background = Color(15, 15, 15)
    }

    fun clearThreadTimer() {
        timer.cancel()
    }
}
