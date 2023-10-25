package ie.setu.domain

import org.joda.time.DateTime

data class User(
    var userId: Int,
    var username: String,
    var email: String,
    var password: String,
    var firstName: String,
    var lastName: String,
    //var dateOfBirth: DateTime,
    var gender: String,
    var registrationDate: DateTime
)
