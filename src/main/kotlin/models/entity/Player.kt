package models.entity

import models.properties.Destructible
import models.properties.Movable

class Player(
    override var health: Double = 100.0
    ) : Movable(), Destructible {
    var name: String? = null
    var level: Int? = null
}