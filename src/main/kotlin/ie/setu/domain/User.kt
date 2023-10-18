package ie.setu.domain

data class User (
    var userId: Int,
    var username: String,
    var email: String,
    var password: String,
    var firstName: String,
    var lastName: String,
    var dateOfBirth: String,
    var gender: String,
    var registrationDate: String
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