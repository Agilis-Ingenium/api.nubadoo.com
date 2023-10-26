package ie.setu.domain.db

import ie.setu.domain.User
import org.jetbrains.exposed.sql.Table
//import ie.setu.domain.db.


object Users : Table("users") {
    val userId = integer("user_id").autoIncrement().primaryKey()
    val username = varchar("username", 50)
    val email = varchar("email", 100)
    val password = varchar("password", 255)
    val firstName = varchar("first_name", 50)
    val lastName = varchar("last_name", 50)
    //val dateOfBirth = datetime("date_of_birth")
    val gender = varchar("gender", 6)
    //val registrationDate = datetime("registration_date")
}
