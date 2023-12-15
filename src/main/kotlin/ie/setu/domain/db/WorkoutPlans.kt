package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.ReferenceOption

/**
 * Represents the "workout_plans" table in the database.
 *
 * This table stores workout plans associated with users, including plan IDs, user IDs, plan names, schedules,
 * and plan dates.
 */
object WorkoutPlans : Table("workout_plans") {
    val planId = integer("plan_id").autoIncrement().primaryKey()
    val userId = integer("user_id").references(Users.userId, onDelete = ReferenceOption.CASCADE)
    val planName = varchar("plan_name",255)
    val schedule = varchar("schedule", 50).nullable()
    val planDate = datetime("plan_date").nullable()
    val goal = varchar("goal", 20)
    val duration = varchar("duration", 20)
    val description = varchar("description", 150)
    val content = text("content")
}