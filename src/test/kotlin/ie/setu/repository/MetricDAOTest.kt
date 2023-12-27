package ie.setu.repository

import ie.setu.domain.db.Metrics
import ie.setu.domain.db.Users
import ie.setu.domain.repository.UserDAO
import ie.setu.domain.repository.MetricDAO
import ie.setu.helpers.metrics
import org.junit.jupiter.api.Assertions.assertEquals
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

//retrieving some test data from Fixtures
val metric1 = metrics.get(0)
val metric2 = metrics.get(1)
val metric3 = metrics.get(2)

class MetricDAOTest {

    companion object {

        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    @Nested
    inner class ReadMetrics {
        @Test
        fun `getting all metrics from a populated table returns all rows`() {
            transaction {

                //Arrange - create and populate table with three metrics
                val metricDAO = populateMetricTable()

                //Act & Assert
                assertEquals(3, metricDAO.getAll().size)
            }
        }

        /* DISABLING FOR INTERIM SUBMISSION - NOT YET IMPLEMENTED

            @Test
            fun `get meal log by id that doesn't exist, results in no meal log returned`() {
                transaction {

                    //Arrange - create and populate table with three users
                    val userDAO = populateMealLogTable()

                    //Act & Assert
                    assertEquals(null, userDAO.findById(4))
                }
            }

            @Test
            fun `get meal log by id that exists, results in a correct meal log returned`() {
                transaction {
                    //Arrange - create and populate table with three users
                    SchemaUtils.create(Users)
                    val userDAO = UserDAO()
                    userDAO.save(user1)
                    userDAO.save(user2)
                    userDAO.save(user3)

                    //Act & Assert
                    assertEquals(null, userDAO.findById(4))
                }

            }
        }


        @Nested
        inner class CreateUsers {
            @Test
            fun `multiple users added to table can be retrieved successfully`() {
                transaction {

                    //Arrange - create and populate table with three users
                    val userDAO = populateUserTable()

                    //Act & Assert
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

                    //Arrange - create and populate table with three users
                    val userDAO = populateUserTable()

                    //Act & Assert
                    assertEquals(3, userDAO.getAll().size)
                    userDAO.delete(4)
                    assertEquals(3, userDAO.getAll().size)
                }
            }

            @Test
            fun `deleting an existing user in table results in record being deleted`() {
                transaction {

                    //Arrange - create and populate table with three users
                    val userDAO = populateUserTable()

                    //Act & Assert
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

                    //Arrange - create and populate table with three users
                    val userDAO = populateUserTable()

                    //Act & Assert
                    val user3Updated = User(3, "new username", "new@email.ie", password = "password123", dateOfBirth = DateTime.parse("2023-10-24"), firstName = "John", lastName = "Doe", gender = "male", registrationDate = DateTime.parse("2023-10-24"))
                    userDAO.update(user3.userId, user3Updated)

                    // reegistrationDate is not an update able field - so force it
                    val toCompare = userDAO.findById(3)
                    if (toCompare != null) {
                        toCompare.registrationDate = null
                    }
                    user3Updated.registrationDate = null


                    assertEquals(user3Updated, toCompare)
                }
            }

            @Test
            fun `updating non-existant user in table results in no updates`() {
                transaction {

                    //Arrange - create and populate table with three users
                    val userDAO = populateUserTable()

                    //Act & Assert
                    val user4Updated = User(3, "new username", "new@email.ie", password = "password123", dateOfBirth = DateTime.parse("2023-10-24"), firstName = "John", lastName = "Doe", gender = "male", registrationDate = DateTime.parse("2023-10-24"))
                    userDAO.update(4, user4Updated)
                    assertEquals(null, userDAO.findById(4))
                    assertEquals(3, userDAO.getAll().size)
                }
            }
        }
        @Test
        fun `get all users over empty table returns none`() {
            transaction {

                //Arrange - create and setup userDAO object
                SchemaUtils.create(Users)
                val userDAO = UserDAO()

                //Act & Assert
                assertEquals(0, userDAO.getAll().size)
            }
        }

        @Test
        fun `get user by email that doesn't exist, results in no user returned`() {
            transaction {

                //Arrange - create and populate table with three users
                val userDAO = populateUserTable()

                //Act & Assert
                assertEquals(null, userDAO.findByEmail(nonExistingEmail))
            }
        }

        @Test
        fun `get user by email that exists, results in correct user returned`() {
            transaction {

                //Arrange - create and populate table with three users
                val userDAO = populateUserTable()

                //Act & Assert
                assertEquals(user2, userDAO.findByEmail(user2.email))
            }
        }

         */

        internal fun populateMetricTable(): MetricDAO {
            SchemaUtils.create(Users)
            val userDAO = UserDAO()
            userDAO.save(userdata1)
            userDAO.save(userdata2)
            userDAO.save(userdata3)
            userDAO.save(userdata4)
            SchemaUtils.create(Metrics)
            val metricDAO = MetricDAO()
            metricDAO.save(metric1)
            metricDAO.save(metric2)
            metricDAO.save(metric3)
            return metricDAO
        }
    }
}