package ie.setu.domain

import org.joda.time.DateTime
import ie.setu.enums.Gender

data class User(
    var userId: Int,
    var username: String,
    var email: String,
    var password: String,
    var firstName: String,
    var lastName: String,
    //var dateOfBirth: DateTime,
    var gender: Gender,
    //var registrationDate: DateTime?
)
