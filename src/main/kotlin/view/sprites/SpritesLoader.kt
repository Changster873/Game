package view.sprites

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class SpritesLoader {
    val sprites = mutableMapOf<String, BufferedImage>()

    fun getSpriteSheet(fileName: String, forParty: String) {
        runCatching { sprites[forParty] = ImageIO.read(File("gameRes/assets/$fileName.png")) }.onFailure {
            it.printStackTrace()
        }
    }
}
