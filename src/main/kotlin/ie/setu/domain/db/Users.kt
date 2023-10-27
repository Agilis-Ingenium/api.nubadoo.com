package ie.setu.domain.db

import ie.setu.domain.User
import ie.setu.domain.db.Activities.nullable
import org.jetbrains.exposed.sql.Table


object Users : Table("users") {
    val userId = integer("user_id").autoIncrement().primaryKey()
    val username = varchar("username", 50)
    val email = varchar("email", 100)
    val password = varchar("password", 255)
    val firstName = varchar("first_name", 50)
    val lastName = varchar("last_name", 50)
    val dateOfBirth = datetime("date_of_birth").nullable()
    val gender = varchar("gender", 6)
    val registrationDate = datetime("registration_date").nullable()
}
