package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.ReferenceOption

object WorkoutPlans : Table("workout_plans") {
    val planId = integer("activity_id").autoIncrement().primaryKey()
    val userId = integer("user_id").references(Users.userId, onDelete = ReferenceOption.CASCADE)
    val planName = varchar("plan_name",255)
    val schedule = integer("schedule").nullable()
    val planDate = datetime("plan_date").nullable()
}