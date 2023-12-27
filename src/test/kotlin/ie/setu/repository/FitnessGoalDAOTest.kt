package ie.setu.repository

import ie.setu.domain.FitnessGoal
import ie.setu.domain.db.FitnessGoals
import ie.setu.domain.db.Users
import ie.setu.domain.repository.FitnessGoalDAO
import ie.setu.domain.repository.UserDAO
import ie.setu.helpers.fitnessgoals
import org.junit.jupiter.api.Assertions.assertEquals
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

//retrieving some test data from Fixtures
val fitnessgoal1 = fitnessgoals.get(0)
val fitnessgoal2 = fitnessgoals.get(1)
val fitnessgoal3 = fitnessgoals.get(2)

class FitnessGoalDAOTest {

    companion object {

        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    @Nested
    inner class ReadFitnessGoal {
        @Test
        fun `getting all fitness goals from a populated table returns all rows`() {
            transaction {

                //Arrange - create and populate table with three goals
                val fitnessGoalDAO = populateFitnessGoalTable()

                //Act & Assert
                assertEquals(3, fitnessGoalDAO.getAll().size)
            }
        }

        @Test
        fun `get fitness goal by id that doesn't exist, results in no fitness goal returned`() {
            transaction {

                //Arrange - create and populate table with three goals
                val fitnessGoalDAO = populateFitnessGoalTable()

                //Act & Assert
                assertEquals(null, fitnessGoalDAO.findById(4))
            }
        }

        @Test
        fun `get fitness goal by id that exists, results in a correct fitness goal returned`() {
            transaction {

                //Arrange - create and populate table with three goals
                val fitnessGoalDAO = populateFitnessGoalTable()

                //Act & Assert
                assertEquals(null, fitnessGoalDAO.findById(4))
            }

        }
    }

    @Nested
    inner class CreateFitnessGoal {
        @Test
        fun `multiple fitness goals added to table can be retrieved successfully`() {
            transaction {

                //Arrange - create and populate table with three users
                val fitnessGoalDAO = populateFitnessGoalTable()

                //Act & Assert
                assertEquals(3, fitnessGoalDAO.getAll().size)
                assertEquals(fitnessgoal1, fitnessGoalDAO.findById(fitnessgoal1.goalId))
                assertEquals(fitnessgoal2, fitnessGoalDAO.findById(fitnessgoal2.goalId))
                assertEquals(fitnessgoal3, fitnessGoalDAO.findById(fitnessgoal3.goalId))
            }
        }
    }
    @Nested
    inner class DeleteFitnessGoals {
        @Test
        fun `deleting a non-existant fitness goal in table results in no deletion`() {
            transaction {

                //Arrange - create and populate table with three users
                val fitnessGoalDAO = populateFitnessGoalTable()

                //Act & Assert
                assertEquals(3, fitnessGoalDAO.getAll().size)
                fitnessGoalDAO.delete(4)
                assertEquals(3, fitnessGoalDAO.getAll().size)
            }
        }

        @Test
        fun `deleting an existing fitness goal in table results in record being deleted`() {
            transaction {

                //Arrange - create and populate table with three users
                val fitnessGoalDAO = populateFitnessGoalTable()

                //Act & Assert
                assertEquals(3, fitnessGoalDAO.getAll().size)
                fitnessGoalDAO.delete(fitnessgoal3.goalId)
                assertEquals(2, fitnessGoalDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class UpdateFitnessGoals {

        @Test
        fun `updating existing fitness goal in table results in successful update`() {
            transaction {

                //Arrange - create and populate table with three users
                val fitnessGoalDAO = populateFitnessGoalTable()

                //Act & Assert
                val fitnessGoal3Updated = FitnessGoal(
                    goalId = 3,
                    userId = 3,
                    goalType = "weight",
                    targetValue = 105.0,
                    targetDate = DateTime.now(),
                    achieved = false)
                fitnessGoalDAO.update(fitnessgoal3.goalId, fitnessGoal3Updated)

                assertEquals(fitnessGoal3Updated, fitnessGoalDAO.findById(3))
            }
        }

        @Test
        fun `updating non-existant fitness goal in table results in no updates`() {
            transaction {

                //Arrange - create and populate table with three users
                val fitnessGoalDAO = populateFitnessGoalTable()

                //Act & Assert
                val fitnessGoal4Updated = FitnessGoal(
                    goalId = 4,
                    userId = 3,
                    goalType = "weight",
                    targetValue = 105.0,
                    targetDate = DateTime.now(),
                    achieved = true)
                fitnessGoalDAO.update(4, fitnessGoal4Updated)
                assertEquals(null, fitnessGoalDAO.findById(4))
                assertEquals(3, fitnessGoalDAO.getAll().size)
            }
        }
    }
    @Test
    fun `get all fitness goals over empty table returns none`() {
        transaction {

            //Arrange - create and setup foodItemDAO object
            SchemaUtils.create(FitnessGoals)
            val fitnessGoalDAO = FitnessGoalDAO()

            //Act & Assert
            assertEquals(0, fitnessGoalDAO.getAll().size)
        }
    }

    internal fun populateFitnessGoalTable(): FitnessGoalDAO{
        SchemaUtils.create(Users)
        val userDAO = UserDAO()
        userDAO.save(userdata1)
        userDAO.save(userdata2)
        userDAO.save(userdata3)
        userDAO.save(userdata4)
        SchemaUtils.create(FitnessGoals)
        val fitnessGoalDAO = FitnessGoalDAO()
        fitnessGoalDAO.save(fitnessgoal1)
        fitnessGoalDAO.save(fitnessgoal2)
        fitnessGoalDAO.save(fitnessgoal3)
        return fitnessGoalDAO
    }
}