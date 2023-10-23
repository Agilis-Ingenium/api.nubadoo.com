package ie.setu.repository

import ie.setu.domain.User
import ie.setu.domain.db.Users
import ie.setu.domain.repository.UserDAO
import ie.setu.helpers.nonExistingEmail
//import ie.setu.helpers.populateUserTable
import ie.setu.helpers.users
//fimport org.junit.jupiter.api.assertE
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

//retrieving some test data from Fixtures
val user1 = users[0]
val user2 = users[1]
val user3 = users[2]

class UserDAOTest {

    companion object {

        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    @Test
    fun `multiple users added to table can be retrieved successfully`() {
        transaction {

            //Arrange - create and populate table with three users
            SchemaUtils.create(Users)
            val userDAO = UserDAO()
            userDAO.save(user1)
            userDAO.save(user2)
            userDAO.save(user3)

            //Act & Assert
            assertEquals(3, userDAO.getAll().size)
            assertEquals(user1, userDAO.findById(user1.userId))
            assertEquals(user2, userDAO.findById(user2.userId))
            assertEquals(user3, userDAO.findById(user3.userId))
        }
    }
}