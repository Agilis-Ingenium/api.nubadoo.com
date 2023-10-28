package ie.setu.domain

import org.joda.time.DateTime

/**
 * Represents a health metric record in the Health Tracker app.
 *
 * Health metrics include various measurements related to a user's physical health and well-being.
 *
 * @property metricId The unique identifier for the health metric.
 * @property userId The user's identifier associated with the health metric.
 * @property weight The user's recorded weight in kilograms.
 * @property height The user's recorded height in meters.
 * @property bmi The user's calculated Body Mass Index (BMI).
 * @property systolicBloodPressure The user's systolic blood pressure reading.
 * @property diastolicBloodPressure The user's diastolic blood pressure reading.
 * @property heartRate The user's recorded heart rate in beats per minute.
 * @property bloodSugar The user's recorded blood sugar level.
 * @property sleepDuration The user's recorded sleep duration in minutes.
 * @property sleepQuality The user's assessment of sleep quality.
 * Possible values are:
 * - "poor"
 * - "fair"
 * - "good"
 * - "very good"
 * - "excellent"
 * @property createdAt The date and time when the health metric was recorded.
 *
 * @author Warren Byron (adapted from SETU Msc Computing Agile Dev course content).
 */
data class Metric (
    var metricId: Int,
    var userId: Int,
    var weight: Double,
    var height: Double,
    var bmi: Double,
    var systolicBloodPressure: Int,
    var diastolicBloodPressure: Int,
    var heartRate: Int,
    var bloodSugar: Double,
    var sleepDuration: Int,
    var sleepQuality: String,
    var createdAt: DateTime
)
