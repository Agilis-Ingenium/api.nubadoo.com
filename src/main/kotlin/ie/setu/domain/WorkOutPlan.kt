package ie.setu.domain

data class WorkOutPlan (
    var planId: Int,
    var userId: Int,
    var planName: String,
    var schedule: String,
    var planDate: String
)
