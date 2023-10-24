package ie.setu.helpers

import ie.setu.domain.User
import okhttp3.internal.userAgent
import org.joda.time.DateTime

val nonExistingEmail = "112233445566778testUser@xxxxx.xx"
val validName = "Test User 1"
val validEmail = "testuser1@test.com"

//val current: DateTime = 1985-12-10

/*
val users = arrayListOf<User>(
    User(username = "Alice Wonderland", email = "alice@wonderland.com", userId = 1, dateOfBirth = current, firstName = "Alice", lastName = "Wonder", password = "12345", gender = "male", registrationDate = current),
    User(username = "Bob Cat", email = "bob@cat.ie", userId = 2, dateOfBirth = current, firstName = "Alice", lastName = "Wonder", password = "12345", gender = "male", registrationDate = current),
    User(username = "Mary Contrary", email = "mary@contrary.com", userId = 3, dateOfBirth = current, firstName = "Alice", lastName = "Wonder", password = "12345", gender = "male", registrationDate = current),
    User(username = "Carol Singer", email = "carol@singer.com", userId = 4, dateOfBirth = current, firstName = "Alice", lastName = "Wonder", password = "12345", gender = "male", registrationDate = current)
)

 */

val users = arrayListOf<User>(
    User(userId = 1, username = "mary_jones", email = "mary@example.com", password = "secure123", firstName = "Mary", lastName = "Jones", registrationDate = DateTime.now(), dateOfBirth = DateTime.now(), gender = "female",),
    User(userId = 2, username = "peter_wilson", email = "peter@example.com", password = "pass1234", firstName = "Peter", lastName = "Wilson", registrationDate = DateTime.now(), dateOfBirth = DateTime.now(), gender = "male"),
    User(userId = 3, username = "john_doe", email = "john@example.com", password = "password123", firstName = "John", lastName = "Doe", registrationDate = DateTime.now(), dateOfBirth = DateTime.now(), gender = "male"),
    User(userId = 4, username = "jane_smith", email = "jane@example.com", password = "secret456", firstName = "Jane", lastName = "Smith", registrationDate = DateTime.now(), dateOfBirth = DateTime.now(), gender = "female")
)