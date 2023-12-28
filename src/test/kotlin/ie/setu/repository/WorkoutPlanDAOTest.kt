package ie.setu.repository

import ie.setu.domain.WorkoutPlan
import ie.setu.domain.db.Users
import ie.setu.domain.db.WorkoutPlans
import ie.setu.domain.repository.UserDAO
import ie.setu.domain.repository.WorkoutPlanDAO
import ie.setu.helpers.workoutplans
import ie.setu.helpers.users
import org.junit.jupiter.api.Assertions.assertEquals
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.joda.time.DateTime

val workoutplan1 = workoutplans.get(0)
val workoutplan2 = workoutplans.get(1)
val workoutplan3 = workoutplans.get(2)

val userdata1 = users.get(0)
val userdata2 = users.get(1)
val userdata3 = users.get(2)
val userdata4 = users.get(3)

class WorkoutPlanDAOTest {

    companion object {

        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    @Nested
    inner class ReadWorkoutPlans {
        @Test
        fun `getting all workout plans from a populated table returns all rows`() {
            transaction {

                val workoutPlanDAO = populateWorkoutPlanTable()

                println(workoutPlanDAO.getAll())

                assertEquals(3, workoutPlanDAO.getAll().size)
            }
        }

        @Test
        fun `get workout plan by id that doesn't exist, results in no workout plan returned`() {
            transaction {

                val workoutPlanDAO = populateWorkoutPlanTable()

                println(workoutPlanDAO.findById(4))

                assertEquals(null, workoutPlanDAO.findById(4))
            }
        }

        @Test
        fun `get workout plan by id that exists, results in a correct workout plan returned`() {
            transaction {

                val workoutPlanDAO = populateWorkoutPlanTable()

                println(workoutplan3)

                assertEquals(null, workoutPlanDAO.findById(4))
            }
        }

        @Test
        fun `get all workout plans over empty table returns none`() {
            transaction {

                SchemaUtils.create(WorkoutPlans)
                val workoutPlanDAO = WorkoutPlanDAO()

                println(workoutPlanDAO.getAll())

                assertEquals(0, workoutPlanDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class CreateWorkoutPlans {
        @Test
        fun `multiple workout plans added to table can be retrieved successfully`() {
            transaction {

                val workoutPlanDAO = populateWorkoutPlanTable()

                assertEquals(3, workoutPlanDAO.getAll().size)
                assertEquals(workoutplan1, workoutPlanDAO.findById(workoutplan1.planId))
                assertEquals(workoutplan2, workoutPlanDAO.findById(workoutplan2.planId))
                assertEquals(workoutplan3, workoutPlanDAO.findById(workoutplan3.planId))
            }
        }
    }

    @Nested
    inner class DeleteWorkoutPlans {
        @Test
        fun `deleting a non-existant workout plans in table results in no deletion`() {
            transaction {

                val workoutPlanDAO = populateWorkoutPlanTable()

                println(workoutPlanDAO.getAll())

                assertEquals(3, workoutPlanDAO.getAll().size)
                workoutPlanDAO.delete(4)
                assertEquals(3, workoutPlanDAO.getAll().size)
            }
        }

        @Test
        fun `deleting an existing workout plan in table results in record being deleted`() {
            transaction {

                val workoutPlanDAO = populateWorkoutPlanTable()

                println(workoutPlanDAO.getAll())

                assertEquals(3, workoutPlanDAO.getAll().size)
                workoutPlanDAO.delete(3)
                assertEquals(2, workoutPlanDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class UpdateWorkoutPlans {

        @Test
        fun `updating existing workout plan in table results in successful update`() {
            transaction {

                val workoutPlanDAO = populateWorkoutPlanTable()

                val workoutPlan3Updated = WorkoutPlan(
                    planId = 3,
                    userId = 1,
                    planName = "My New Plan",
                    schedule = "Daily",
                    planDate = DateTime.parse("2023-10-24"),
                    goal = "weight loss",
                    duration = "4 weeks",
                    content = "",
                    description = "This is a description")
                workoutPlanDAO.update(workoutplan3.planId, workoutPlan3Updated)

                val toCompare = workoutPlanDAO.findById(3)

                println(toCompare)
                println("---")
                println(workoutPlan3Updated)

                assertEquals(workoutPlan3Updated, toCompare)
            }
        }

        @Test
        fun `updating non-existant workout plan in table results in no updates`() {
            transaction {

                val workoutPlanDAO = populateWorkoutPlanTable()

                val workoutPlan4Updated = WorkoutPlan(
                    planId = 4,
                    userId = 1,
                    planName = "My Very New Plan",
                    schedule = "Alternative days",
                    planDate = DateTime.parse("2023-10-24"),
                    goal = "weight loss",
                    content = "",
                    description = "This is a description",
                    duration = "4 weeks")

                println(workoutPlanDAO.findById(4))
                println("---")
                println(workoutPlanDAO.getAll().size)

                workoutPlanDAO.update(4, workoutPlan4Updated)
                assertEquals(null, workoutPlanDAO.findById(4))
                assertEquals(3, workoutPlanDAO.getAll().size)
            }
        }
    }

    internal fun populateWorkoutPlanTable(): WorkoutPlanDAO{
        SchemaUtils.create(Users)
        val userDAO = UserDAO()
        userDAO.save(userdata1)
        userDAO.save(userdata2)
        userDAO.save(userdata3)
        userDAO.save(userdata4)

        SchemaUtils.create(WorkoutPlans)
        val workoutPlanDAO = WorkoutPlanDAO()
        workoutPlanDAO.save(workoutplan1)
        workoutPlanDAO.save(workoutplan2)
        workoutPlanDAO.save(workoutplan3)

        return workoutPlanDAO
    }
}