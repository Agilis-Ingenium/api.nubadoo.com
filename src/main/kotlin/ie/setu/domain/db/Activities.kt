package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.ReferenceOption

// SRP - Responsibility is to manage one user.
//       Database wise, this is the table object.

object Activities : Table("activity_data") {
    val activityId = integer("activity_id").autoIncrement().primaryKey()
    val userId = integer("user_id").references(Users.userId, onDelete = ReferenceOption.CASCADE)
    val activityType = varchar("activity_type",10)
    val durationMinutes = integer("duration_minutes")
    val distanceKM = double("distance_km").nullable()
    val workoutIntensity = varchar("workout_intensity", 10)
    val activityDate = datetime("activity_date")
}

