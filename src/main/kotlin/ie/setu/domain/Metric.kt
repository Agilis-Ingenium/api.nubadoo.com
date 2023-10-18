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

/*
CREATE TABLE metrics (
metric_id SERIAL PRIMARY KEY,
user_id INT NOT NULL,
weight DECIMAL(5, 2),
height DECIMAL(5, 2),
bmi DECIMAL(5, 2),
systolic_blood_pressure INT,
diastolic_blood_pressure INT,
heart_rate INT,
blood_sugar DECIMAL(5, 2),
sleep_duration INT,
sleep_quality sleep_quality,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (user_id) REFERENCES users(user_id)
);
*/