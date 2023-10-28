package ie.setu.domain

import org.joda.time.DateTime

/**
 * Represents a user in the Health Tracker app.
 *
 * Users are individuals who use the Health Tracker app and can log health data and set fitness goals.
 *
 * @property userId The unique identifier for the user.
 * @property username The user's username.
 * @property email The user's email address.
 * @property password The user's password (encrypted or hashed for security).
 * @property firstName The user's first name.
 * @property lastName The user's last name.
 * @property dateOfBirth The user's date of birth (nullable).
 * @property gender The user's gender.
 * Possible values are:
 * - "male"
 * - "female"
 * @property registrationDate The date and time when the user registered with the app (nullable).
 *
 * @author Warren Byron (adapted from SETU Msc Computing Agile Dev course content).
 */
data class User(
    var userId: Int,
    var username: String,
    var email: String,
    var password: String,
    var firstName: String,
    var lastName: String,
    var dateOfBirth: DateTime?,
    var gender: String,
    var registrationDate: DateTime?
)
