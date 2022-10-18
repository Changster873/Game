import org.newdawn.slick.Music
import org.newdawn.slick.Sound

class SoundSystem {
    companion object {
        val musicMap = mutableMapOf<String, Music>()
        val soundMap = mutableMapOf<String, Sound>()
    }

    fun load() {
        runCatching {
            soundMap["collision"] = Sound("/gameRes/sound/click_sound_2.wav")
            soundMap["menu-click"] = Sound("gameRes/sound/click_sound.ogg")
        }.onFailure {
            it.printStackTrace()
        }
    }
}
