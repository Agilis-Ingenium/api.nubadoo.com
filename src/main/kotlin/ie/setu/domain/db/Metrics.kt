package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.ReferenceOption

object Metrics : Table("metrics") {
    val metricId = integer("metric_id").autoIncrement().primaryKey()
    val userId = integer("user_id").references(Users.userId, onDelete = ReferenceOption.CASCADE)
    val weight = double("weight")
    val height = double("height")
    val bmi = double("bmi")
    val systolicBloodPressure = integer("systolic_blood_pressure")
    val diastolicBloodPressure = integer("diastolic_blood_pressure")
    val heartRate = integer("hear_rate")
    val bloodSugar = double("blood_sugar")
    val sleepDuration = integer("sleep_duration")
    val sleepQuality = varchar("activity_type",10)      // ENUM
    val createdAt = datetime("created_at")
}