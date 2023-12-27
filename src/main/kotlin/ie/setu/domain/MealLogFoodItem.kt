package ie.setu.domain

/**
 * Represents a food item within a meal log in the Health Tracker app.
 *
 * Meal log food items associate specific food items with meal logs and include information about the quantity and calories.
 *
 * @property mealLogId The unique identifier of the meal log associated with this food item.
 * @property foodId The unique identifier of the food item.
 * @property quantity The quantity of the food item consumed.
 * @property calories The calories associated with the specific quantity of the food item.
 *
 * @author Warren Byron (adapted from SETU Msc Computing Agile Dev course content).
 */
data class MealLogFoodItem(
    val mealLogId: Int?,
    val foodId: Int,
    val quantity: Double,
    val calories: Int?
)
