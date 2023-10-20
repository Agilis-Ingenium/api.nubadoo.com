package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table

object FoodItems : Table("food_items") {
    val foodId = integer("food_id").autoIncrement().primaryKey()
    val name = varchar("name",255)
    val calories = integer("calories")
    val carbohydrates = double("carbohydrates")
    val proteins = double("proteins")
    val fats = double("fats")
    val vitamins = double("proteins").nullable()
    val minerals = varchar("minerals", 255).nullable()
}