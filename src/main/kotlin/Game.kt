/**
 * Main entry point of the game.
 */
fun main(args: Array<String>) {
    loadAssets()
}

/**
 * Loads all assets for the game to start.
 */
fun loadAssets() {
    val game = Game()
    game.initialise()
}