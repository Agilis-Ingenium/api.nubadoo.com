package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.ReferenceOption

object FitnessGoals : Table("fitness_goals") {
    val goalId = integer("goal_id").autoIncrement().primaryKey()
    val userId = integer("user_id").references(Users.userId, onDelete = ReferenceOption.CASCADE)
    val goalType = varchar("goal_type",10)           // ENUM
    val targetValue = double("target_value")
    val targetDate = datetime("target_date").nullable()
}