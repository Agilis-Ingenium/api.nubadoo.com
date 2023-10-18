package ie.setu.domain

data class MealLog (
    var logId: Int,
    var userId: Int,
    var mealTime: String,
    var totalCalories: Int
)
