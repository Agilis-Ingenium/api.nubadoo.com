package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.ReferenceOption

object MealLogs : Table("meal_logs") {
    val logId = integer("logy_id").autoIncrement().primaryKey()
    val userId = integer("user_id").references(Users.userId, onDelete = ReferenceOption.CASCADE)
    val meal_time = datetime("activity_date")
    val total_calories = integer("total_calories").nullable()
}