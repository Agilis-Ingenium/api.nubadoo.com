package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.ReferenceOption

/**
 * Represents the "meal_logs" table in the database.
 *
 * This table stores meal logs, including information about the user, meal time, and total calories consumed.
 */
object MealLogs : Table("meal_logs") {
    val logId = integer("log_id").autoIncrement().primaryKey()
    val userId = integer("user_id").references(Users.userId, onDelete = ReferenceOption.CASCADE)
    val mealTime = datetime("meal_time")
    val totalCalories = integer("total_calories").nullable()
}