import javazoom.jl.player.Player
import java.io.FileInputStream

/**
 * The main game sound system.
 */
class SoundSystem : Thread() {
    private var player: Player? = null
    var fileName: String? = null

    /**
     * Given the new music, it will play that and stop the old one if any.
     */
    fun changeMusic(newFile: String) {
        stopMusic()
        fileName = newFile
        startMusic()
    }

    /**
     * Retrieves given file to play the music.
     */
    private fun startMusic() {
        val music = FileInputStream(fileName)
        player = Player(music)
        player!!.play()
    }

    /**
     * Stops the current music that is playing.
     */
    fun stopMusic() {
        player?.close()
    }

    /**
     * Will play the music with a runnable thread so it can be terminated.
     */
    override fun run() {
        startMusic()
    }
}