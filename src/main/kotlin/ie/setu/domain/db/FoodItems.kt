package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table

/**
 * Represents the "food_items" table in the database.
 *
 * This table stores information about food items, including their name, nutritional values, vitamins, and minerals.
 */
object FoodItems : Table("food_items") {
    val foodItemId = integer("food_id").autoIncrement().primaryKey()
    val name = varchar("name",255)
    val calories = integer("calories")
    val carbohydrates = double("carbohydrates")
    val proteins = double("proteins")
    val fats = double("fats")
    val vitamins = varchar("vitamins", 255).nullable()
    val minerals = varchar("minerals", 255).nullable()
}