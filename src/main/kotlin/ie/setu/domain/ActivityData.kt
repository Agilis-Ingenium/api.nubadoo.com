package ie.setu.domain

data class ActivityData (
    var activityId: Int,
    var userId: Int,
    var activityType:String,
    var durationMinutes:Int,
    var distanceKm:Double,
    var workoutIntensity:String,
    var activityDate:String
)
