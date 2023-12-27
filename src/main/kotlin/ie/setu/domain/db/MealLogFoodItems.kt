package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.ReferenceOption

/**
 * Represents the "meal_log_food_items" table in the database.
 *
 * This table stores meal logs, including information about the food, quantity, and calories consumed by the user per food item during a meal.
 */
object MealLogFoodItems : Table("meal_log_food_items") {

    val mealLogId = integer("meal_log_id").autoIncrement().primaryKey()
    val foodId = integer("food_id").references(FoodItems.foodItemId, onDelete = ReferenceOption.CASCADE)
    val quantity = integer("quantity")
    val calories = integer("calories").nullable()
}
