package models.properties

import java.awt.image.BufferedImage

/**
 * Warmonger is an entity that can deal damage to other objects or entities.
 */
interface Warmonger {
    fun attackSprites(): List<BufferedImage>
}
