package ie.setu.domain

import org.joda.time.DateTime

/**
 * Represents a workout plan in the Health Tracker app.
 *
 * Workout plans are designed by users to outline their exercise routines and schedules.
 *
 * @property planId The unique identifier for the workout plan.
 * @property userId The user's identifier associated with the workout plan.
 * @property planName The name or title of the workout plan.
 * @property schedule The planned schedule for the workout plan (nullable).
 * @property planDate The date when the workout plan was created (nullable).
 *
 * @author Warren Byron (adapted from SETU Msc Computing Agile Dev course content).
 */
data class WorkoutPlan (
    var planId: Int,
    var userId: Int,
    var planName: String,
    var schedule: String?,
    var planDate: DateTime?,
    var goal: String?,
    var duration: String,
    var description: String,
    var content: String
)