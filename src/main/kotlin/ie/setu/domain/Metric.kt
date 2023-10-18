package ie.setu.domain

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
    var createdAt: String
)
