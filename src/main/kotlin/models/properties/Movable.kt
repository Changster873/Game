package models.properties

/**
 * Objects that can be moved or entities that can move
 */
abstract class Movable() {
    private var location: Location = Location(0, 0)

    fun moveLeft() {
        location.direction = DIRECTION.WEST
        if (location.xPos - 1 < Location.Position.MIN_POS) return
        location.xPos -= 1
    }

    fun moveRight() {
        location.direction = DIRECTION.EAST
        if (location.xPos + 1 > Location.Position.MAX_POS) return
        location.xPos += 1
    }

    fun moveUp() {
        location.direction = DIRECTION.NORTH
        if (location.yPos - 1 < Location.Position.MIN_POS) return
        location.yPos -= 1
    }

    fun moveDown() {
        location.direction = DIRECTION.SOUTH
        if (location.yPos + 1 > Location.Position.MAX_POS) return
        location.yPos += 1
    }
}

interface MovableSpace {

}

/**
 * Stores the location information.
 *
 * Includes properties such as the x position, y position and the direction the object is facing.
 */
class Location(x: Int, y: Int) {
    object Position {
        const val MIN_POS = 0
        const val MAX_POS = 19
    }
    var xPos = x
    var yPos = y
    var direction = DIRECTION.SOUTH
}

enum class DIRECTION {
    NORTH,
    SOUTH,
    WEST,
    EAST
}