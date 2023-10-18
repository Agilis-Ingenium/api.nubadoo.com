package ie.setu.domain

data class FitnessGoal (
    var goal_id: Int,
    var user_id: Int,
    var goal_type: String,
    var target_value: Double,
    var target_date: String,
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