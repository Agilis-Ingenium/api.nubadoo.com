package ie.setu.domain

data class FitnessGoal (
    var goalId: Int,
    var userId: Int,
    var goalType: String,
    var targetValue: Double,
    var targetDate: String,
)

/*
CREATE TABLE fitness_goals (
goal_id SERIAL PRIMARY KEY,
user_id INT NOT NULL,
goal_type goal_type NOT NULL,
target_value DECIMAL(10, 2) NOT NULL,
target_date DATE,
FOREIGN KEY (user_id) REFERENCES users(user_id)
);

 */