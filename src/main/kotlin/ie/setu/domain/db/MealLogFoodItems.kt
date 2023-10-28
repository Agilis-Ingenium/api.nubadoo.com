package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.ReferenceOption

/**
 * Represents the "meal_log_food_items" table in the database.
 *
 * This table stores information about food items associated with meal logs, including their quantities and calories.
 */
object MealLogFoodItems : Table("meal_log_food_items") {
    val mealLogId = integer("meal_log_id").references(MealLogs.logId, onDelete = ReferenceOption.CASCADE)
    val foodId = integer("food_id").references(FoodItems.foodItemId, onDelete = ReferenceOption.CASCADE)
    val quantity = double("quantity")
    val calories = integer("calories")
}