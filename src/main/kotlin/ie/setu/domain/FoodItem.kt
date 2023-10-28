package ie.setu.domain

/**
 * Represents a food item in the Health Tracker app.
 *
 * Food items include various types of food that users can log to track their nutritional intake.
 *
 * @property foodItemId The unique identifier for the food item.
 * @property name The name or title of the food item.
 * @property calories The number of calories in the food item.
 * @property carbohydrates The amount of carbohydrates in the food item.
 * @property proteins The amount of proteins in the food item.
 * @property fats The amount of fats in the food item.
 * @property vitamins Additional information about vitamins in the food item (nullable).
 * @property minerals Additional information about minerals in the food item (nullable).
 *
 * @author Warren Byron (adapted from SETU Msc Computing Agile Dev course content).
 */
data class FoodItem (
    var foodItemId: Int,
    var name: String,
    var calories: Int,
    var carbohydrates: Double,
    var proteins: Double,
    var fats: Double,
    var vitamins: String?,
    var minerals: String?
)
