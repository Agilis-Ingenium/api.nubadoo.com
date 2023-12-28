package ie.setu.repository

import ie.setu.domain.User
import ie.setu.domain.db.Users
import ie.setu.domain.repository.UserDAO
import ie.setu.helpers.users
import org.junit.jupiter.api.Assertions.assertEquals
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import ie.setu.helpers.nonExistingEmail
import org.joda.time.DateTime

val user1 = users.get(0)
val user2 = users.get(1)
val user3 = users.get(2)

class UserDAOTest {

    companion object {

        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    @Nested
    inner class ReadUsers {
        @Test
        fun `getting all users from a populated table returns all rows`() {
            transaction {

                val userDAO = populateUserTable()

                println(userDAO.getAll())

                assertEquals(3, userDAO.getAll().size)
            }
        }

        @Test
        fun `get user by id that doesn't exist, results in no user returned`() {
            transaction {

                val userDAO = populateUserTable()

                println(userDAO.findById(4))

                assertEquals(null, userDAO.findById(4))
            }
        }

        @Test
        fun `get user by id that exists, results in a correct user returned`() {
            transaction {

                val userDAO = populateUserTable()

                println(user3)

                assertEquals(user3, userDAO.findById(3))
            }
        }

        @Test
        fun `get all users over empty table returns none`() {
            transaction {

                SchemaUtils.create(Users)
                val userDAO = UserDAO()

                println(userDAO.getAll())

                assertEquals(0, userDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class CreateUsers {
        @Test
        fun `multiple users added to table can be retrieved successfully`() {
            transaction {

                val userDAO = populateUserTable()

                println(userDAO.getAll())

                assertEquals(3, userDAO.getAll().size)
                assertEquals(user1, userDAO.findById(user1.userId))
                assertEquals(user2, userDAO.findById(user2.userId))
                assertEquals(user3, userDAO.findById(user3.userId))
            }
        }
    }

    @Nested
    inner class DeleteUsers {
        @Test
        fun `deleting a non-existant user in table results in no deletion`() {
            transaction {

                val userDAO = populateUserTable()

                println(userDAO.getAll())

                assertEquals(3, userDAO.getAll().size)
                userDAO.delete(4)
                assertEquals(3, userDAO.getAll().size)
            }
        }

        @Test
        fun `deleting an existing user in table results in record being deleted`() {
            transaction {

                val userDAO = populateUserTable()

                println(userDAO.getAll())

                assertEquals(3, userDAO.getAll().size)
                userDAO.delete(user3.userId)
                assertEquals(2, userDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class UpdateUsers {

        @Test
        fun `updating existing user in table results in successful update`() {
            transaction {

                val userDAO = populateUserTable()

                val user3Updated = User(
                    3,
                    "new username",
                    "new@email.ie",
                    password = "password123",
                    dateOfBirth = DateTime.parse("2023-10-24"),
                    firstName = "John",
                    lastName = "Doe",
                    gender = "male",
                    registrationDate = DateTime.parse("2023-10-24"))
                userDAO.update(user3.userId, user3Updated)

                val toCompare = userDAO.findById(3)

                println(toCompare)
                println("---")
                println(user3Updated)

                assertEquals(user3Updated, toCompare)
            }
        }

        @Test
        fun `updating non-existant user in table results in no updates`() {
            transaction {

                val userDAO = populateUserTable()

                val user4Updated = User(
                    3,
                    "new username",
                    "new@email.ie",
                    password = "password123",
                    dateOfBirth = DateTime.parse("2023-10-24"),
                    firstName = "John",
                    lastName = "Doe",
                    gender = "male",
                    registrationDate = DateTime.parse("2023-10-24"))

                println(userDAO.findById(4))
                println("---")
                println(userDAO.getAll().size)

                userDAO.update(4, user4Updated)

                assertEquals(null, userDAO.findById(4))
                assertEquals(3, userDAO.getAll().size)
            }
        }
    }

    @Test
    fun `get user by email that doesn't exist, results in no user returned`() {
        transaction {

            val userDAO = populateUserTable()

            println(nonExistingEmail)

            assertEquals(null, userDAO.findByEmail(nonExistingEmail))
        }
    }

    @Test
    fun `get user by email that exists, results in correct user returned`() {
        transaction {

            val userDAO = populateUserTable()

            println(user2.email)
            println("---")
            println(user2)

            assertEquals(user2, userDAO.findByEmail(user2.email))
        }
    }

    internal fun populateUserTable(): UserDAO{
        SchemaUtils.create(Users)
        val userDAO = UserDAO()
        userDAO.save(user1)
        userDAO.save(user2)
        userDAO.save(user3)
        return userDAO
    }
}