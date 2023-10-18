package ie.setu.domain

data class MealLog (
    var logId: Int,
    var userId: Int,
    var mealTime: String,
    var totalCalories: Int
)


/*
CREATE TABLE meal_logs (
    log_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    meal_time TIMESTAMP NOT NULL,
    total_calories INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
*/