package ie.setu.domain

data class MealLogFoodItem (
    var meal_log_id: Int,
    var food_id: Int,
    var quantity: Double,
    var calories: Int
)

/*
    meal_log_id INT NOT NULL,
    food_id INT NOT NULL,
    quantity DECIMAL(8, 2) NOT NULL,
    calories INT,
    FOREIGN KEY (meal_log_id) REFERENCES meal_logs(log_id),
    FOREIGN KEY (food_id) REFERENCES food_items(food_id)
 */