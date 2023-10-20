package ie.setu.domain

import org.joda.time.DateTime
data class Activity (
    var activityId: Int,
    var userId: Int,
    var activityType:String,
    var durationMinutes:Int,
    var distanceKm:Double?,
    var workoutIntensity:String,
    var activityDate:DateTime
)
