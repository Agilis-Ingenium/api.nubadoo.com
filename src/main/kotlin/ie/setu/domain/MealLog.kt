package ie.setu.domain

import org.joda.time.DateTime

data class MealLog (
    var logId: Int,
    var userId: Int,
    var mealTime: DateTime,
    var totalCalories: Int?
)
