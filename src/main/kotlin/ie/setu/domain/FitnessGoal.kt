package ie.setu.domain

import org.joda.time.DateTime

data class FitnessGoal (
    var goalId: Int,
    var userId: Int,
    var goalType: String,
    var targetValue: Double,
    var targetDate: DateTime?
)
