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
