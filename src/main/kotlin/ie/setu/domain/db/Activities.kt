package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.ReferenceOption

/**
 * Represents the "activity_data" table in the database.
 *
 * This table stores data related to user activities, including exercise and physical activities.
 */
object Activities : Table("activity_data") {
    val activityId = integer("activity_id").autoIncrement().primaryKey()
    val userId = integer("user_id").references(Users.userId, onDelete = ReferenceOption.CASCADE)
    val activityType = varchar("activity_type",20)
    val durationMinutes = integer("duration_minutes")
    val distanceKM = double("distance_km").nullable()
    val workoutIntensity = varchar("workout_intensity", 20)
    val activityDate = datetime("activity_date")
}

