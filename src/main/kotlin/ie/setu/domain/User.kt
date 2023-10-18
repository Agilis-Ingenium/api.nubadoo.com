package ie.setu.domain

data class User (
    var user_id: Int,
    var username: String,
    var email: String,
    var password: String,
    var first_name: String,
    var last_name: String,
    var date_of_birth: String,
    var gender: String,
    var registration_date: String
)

/*
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    date_of_birth DATE,
    gender gender NOT NULL,
    registration_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
 */