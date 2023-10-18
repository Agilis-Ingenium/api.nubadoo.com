package ie.setu.domain

data class WorkOutPlan (
    var plan_id: Int,
    var user_id: Int,
    var plan_name: String,
    var schedule: String,
    var plan_date: String
)

/*
CREATE TABLE workout_plans (
plan_id SERIAL PRIMARY KEY,
user_id INT NOT NULL,
plan_name VARCHAR(255) NOT NULL,
schedule VARCHAR(255),
plan_date DATE,
FOREIGN KEY (user_id) REFERENCES users(user_id)
);

 */