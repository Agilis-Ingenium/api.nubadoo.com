package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.ReferenceOption

object MealLogFoodItems : Table("meal_log_food_items") {
    val mealLogId = integer("meal_log_id").references(MealLogs.mealLogId, onDelete = ReferenceOption.CASCADE)
    val foodId = integer("food_id").references(FoodItems.foodId, onDelete = ReferenceOption.CASCADE)
    val quantity = double("quantity")
    val calories = integer("calories")
}