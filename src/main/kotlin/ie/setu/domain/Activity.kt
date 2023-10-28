package ie.setu.domain

import org.joda.time.DateTime

/**
 * Represents an activity recorded in the Health Tracker app.
 *
 * Activities include various exercises and physical activities performed by users.
 *
 * @property activityId The unique identifier for the activity.
 * @property userId The user's identifier associated with the activity.
 * @property activityType The type of activity, e.g., "Running," "Swimming," "Yoga."
 * Possible values are:
 * - yoga
 * - aerobic exercise
 * - strength training
 * - running
 * - swimming
 * - walking
 * - bicycling
 * - bymnastics
 * - water aerobics
 * - dance
 * - tennis
 * - pilates
 * - hiking
 * - weightlifting
 * - jogging
 * - power walking
 * - gardening
 * - inline skating
 * - other
 * @property durationMinutes The duration of the activity in minutes.
 * @property distanceKm The distance covered during the activity in kilometers (nullable).
 * @property workoutIntensity The intensity level of the activity (e.g., "Low," "High").
 * Possible values are:
 * - LOW
 * - LIGHT
 * - MODERATE
 * - LOW_TO_MODERATE
 * - MODERATE_INTENSITY
 * - HIGH
 * - HIGH_INTENSITY
 * - VERY_HIGH
 * - EXTREME
 * - VARIED
 * @property activityDate The date and time when the activity was recorded.
 *
 * @author Warren Byron (adapted from SETU Msc Computing Agile Dev course content).
 */
data class Activity (
    var activityId: Int,
    var userId: Int,
    var activityType:String,
    var durationMinutes:Int,
    var distanceKm:Double?,
    var workoutIntensity:String,
    var activityDate:DateTime
)
