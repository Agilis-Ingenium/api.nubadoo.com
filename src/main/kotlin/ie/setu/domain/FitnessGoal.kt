package ie.setu.domain

data class FitnessGoal (
    var goalId: Int,
    var userId: Int,
    var goalType: String,
    var targetValue: Double,
    var targetDate: String,
)
