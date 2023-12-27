package ie.setu.domain

import org.joda.time.DateTime

/**
 * Represents a meal log in the Health Tracker app.
 *
 * Meal logs are used to record information about meals, such as the time they were consumed and their total calories.
 *
 * @property logId The unique identifier for the meal log.
 * @property userId The user's identifier associated with the meal log.
 * @property mealTime The date and time when the meal was consumed.
 * @property totalCalories The total calories of the meal (nullable).
 *
 * @author Warren Byron (adapted from SETU Msc Computing Agile Dev course content).
 */
data class MealLog(
    val logId: Int?,
    val userId: Int,
    val mealTime: DateTime,
    val totalCalories: Int?,
    val foodItems: List<FoodItem>
)
