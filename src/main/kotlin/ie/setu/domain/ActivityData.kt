package ie.setu.domain

data class ActivityData (
    var activity_id: Int,
    var user_id: Int,
    var activity_type:String,
    var duration_minutes:Int,
    var distance_km:Double,
    var workout_intensity:String,
    var activity_date:String
)

/*
CREATE TABLE activity_data (
    activity_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    activity_type activity_type NOT NULL,           --
    duration_minutes INT NOT NULL,                  --
    distance_km NUMERIC(2),                         --
    workout_intensity workout_intensity NOT NULL,   --
    activity_date DATE NOT NULL,                    --
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
 */