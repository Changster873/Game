package view.sprites

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
 * The visual sprites that represent the game models.
 */
class Sprites {
    companion object {
        fun get(fileName: List<String>): List<BufferedImage> {
            return fileName.map {
                ImageIO.read(File("src/main/resources/assets/$it.png"))
            }
        }

        fun spriteFileRange(list: List<Int>, prefix: String): List<String> {
            return list.map {
                "${prefix}${if (it.length() == 1) "00" else "0"}$it"
            }
        }
    }
}

/**
 * Key and value pairs of all sprite paths.
 */
enum class FilePath(val path: String) {
    Player("player/tile");
}

/**
 * Return the number of digits of this number.
 */
fun Int.length() = when (this) {
    0 -> 1
    else -> kotlin.math.log10(kotlin.math.abs(toDouble())).toInt() + 1
}
