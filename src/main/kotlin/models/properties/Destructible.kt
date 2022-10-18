package models.properties

import java.awt.image.BufferedImage

/**
 * Object can be destroyed or entity can perish.
 */
interface Destructible {
    /*
        Health: How much damage does this object need to take to perish/be destroyed
     */
    var health: Double

}
