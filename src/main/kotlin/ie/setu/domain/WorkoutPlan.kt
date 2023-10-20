package ie.setu.domain

import org.joda.time.DateTime

data class WorkoutPlan (
    var planId: Int,
    var userId: Int,
    var planName: String,
    var schedule: String?,
    var planDate: DateTime?
)
