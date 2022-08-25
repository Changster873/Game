import view.Screen

/**
 * Main entry point of the game.
 */
fun main(args: Array<String>) {
    val screen = Screen()
    State.game = Game()
    State.game!!.screen = screen
}