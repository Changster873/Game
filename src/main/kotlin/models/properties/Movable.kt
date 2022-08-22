package models.properties

/**
 * Objects that can be moved or entities that can move
 */
abstract class Movable(loc: Location) {
    private val location: Location = loc

    fun turnWest() {
        location.direction = DIRECTION.WEST
    }

    fun turnEast() {
        location.direction = DIRECTION.EAST
    }

    fun turnNorth() {
        location.direction = DIRECTION.NORTH
    }

    fun turnSouth() {
        location.direction = DIRECTION.SOUTH
    }

    fun moveLeft() {
        if (location.x - 1 < Location.Position.MIN_POS) return
        location.x -= 1
    }

    fun moveRight() {
        if (location.x + 1 > Location.Position.MAX_POS) return
        location.x += 1
    }

    fun moveUp() {
        if (location.y - 1 < Location.Position.MIN_POS) return
        location.y -= 1
    }

    fun moveDown() {
        if (location.y + 1 > Location.Position.MAX_POS) return
        location.y += 1
    }
}

/**
 * Stores the location information.
 *
 * Includes properties such as the x position, y position and the direction the object is facing.
 */
class Location(xPos: Int, yPos: Int) {
    /**
     * 20 x 20 grid world map
     */
    object Position {
        const val MIN_POS = 0
        const val MAX_POS = 19
    }

    var x = xPos
    var y = yPos
    var direction = DIRECTION.SOUTH
}

/**
 * Direction the entity is facing.
 */
enum class DIRECTION {
    NORTH,
    SOUTH,
    WEST,
    EAST
}