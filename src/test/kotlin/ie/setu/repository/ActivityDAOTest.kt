package ie.setu.repository

import ie.setu.domain.db.Users
import ie.setu.domain.db.Activities
import ie.setu.domain.repository.UserDAO
import ie.setu.domain.repository.ActivityDAO
import ie.setu.helpers.activities
import org.junit.jupiter.api.Assertions.assertEquals
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

//retrieving some test data from Fixtures
val activity1 = activities.get(0)
val activity2 = activities.get(1)
val activity3 = activities.get(2)

class ActivityDAOTest {

    companion object {

        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    @Nested
    inner class ReadActivities {
        @Test
        fun `getting all activities from a populated table returns all rows`() {
            transaction {

                //Arrange - create and populate table with three workoutplans
                val activityDAO = populateActivityTable()

                //Act & Assert
                assertEquals(3, activityDAO.getAll().size)
            }
        }

        @Test
        fun `get activity by id that doesn't exist, results in no activity returned`() {
            transaction {

                //Arrange - create and populate table with three activities
                val activityDAO = populateActivityTable()

                //Act & Assert
                assertEquals(null, activityDAO.findByActivityId(4))
            }
        }

        @Test
        fun `get activity plan by id that exists, results in a correct activity returned`() {
            transaction {
                //Arrange - create and populate table with three workout plans
                val activityDAO = populateActivityTable()

                //Act & Assert
                assertEquals(null, activityDAO.findByActivityId(4))
            }

        }

        @Test
        fun `get all activities over empty table returns none`() {
            transaction {

                //Arrange - create and setup userDAO object
                SchemaUtils.create(Activities)
                val activityDAO = ActivityDAO()

                //Act & Assert
                assertEquals(0, activityDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class CreateActivity {
        @Test
        fun `multiple activities added to table can be retrieved successfully`() {
            transaction {

                //Arrange - create and populate table with three users
                val activityDAO = populateActivityTable()

                //Act & Assert
                assertEquals(3, activityDAO.getAll().size)
                assertEquals(activity1, activityDAO.findByActivityId(activity1.activityId))
                assertEquals(activity2, activityDAO.findByActivityId(activity2.activityId))
                assertEquals(activity3, activityDAO.findByActivityId(activity3.activityId))
            }
        }
    }

    // THERE ARE NO DELETE FUNCTIONS FOR ACTIVITIES YET

    //@Nested
    //inner class DeleteActivities {
        //@Test
        //fun `deleting a non-existant activity in table results in no deletion`() {
            //transaction {

                //Arrange - create and populate table with three users
                //val activityDAO = populateActivityTable()

                //Act & Assert
                //assertEquals(3, activityDAO.getAll().size)
                //activityDAO..delete(4)
                //assertEquals(3, activityDAO.getAll().size)
            //}
        //}

        //@Test
        //fun `deleting an existing activity in table results in record being deleted`() {
            //transaction {

                //Arrange - create and populate table with three activites
                //val activityDAO = populateActivityTable()

                //Act & Assert
                //assertEquals(3, activityDAO.getAll().size)
                //activityDAO.delete(user3.userId)
                //assertEquals(2, activityDAO.getAll().size)
            //}
        //}
    //}

    //@Nested
    //inner class UpdateActivities {

        // NO UPDATE FUNCTION ON ACTIVITIES YET

        //@Test
        //fun `updating existing activities in table results in successful update`() {
        //    transaction {

                //Arrange - create and populate table with three users
                //val activityDAO = populateActivityTable()

                //Act & Assert
                //val activity3Updated = Activity(
                //    activityId = 4,
                //    userId = 1,
                //    activityType = "swimming",
                //    durationMinutes = 30,
                //    distanceKm = 2.2,
                //    workoutIntensity = "high",
                //    activityDate = DateTime.now()
                //)
                //activityDAO.save(activity3.activityId, activity3Updated)

                // planDate is not an update able field - so force it
                //val toCompare = activityDAO.findById(3)
                //if (toCompare != null) {
                //    toCompare.planDate = null
                //}
                //activity3Updated.planDate = null

                //assertEquals(activity3Updated, toCompare)
            //}
        //}

        //@Test
        //fun `updating non-existant aactivity in table results in no updates`() {
            //transaction {

                //Arrange - create and populate table with three activities
                //val activityDAO = populateActivityTable()

                //Act & Assert
                //val activity4Updated = Activity(
                        //activityId = 4,
                        //userId = 1,
                        //activityType = "swimming",
                        //durationMinutes = 30,
                        //distanceKm = 2.2,
                        //workoutIntensity = "high",
                        //activityDate = DateTime.now()
                //)
                //activityDAO.update(4, activity4Updated)
                //assertEquals(null, activityDAO.findById(4))
               // assertEquals(3, activityDAO.getAll().size)
            //}
        //}
    //}

    internal fun populateActivityTable(): ActivityDAO{

        // Users tables already in the temp DB ? ? ? ? Not sure - but seems so

        SchemaUtils.create(Users)
        val userDAO = UserDAO()
        userDAO.save(userdata1)
        userDAO.save(userdata2)
        userDAO.save(userdata3)
        userDAO.save(userdata4)

        SchemaUtils.create(Activities)
        val activityDAO = ActivityDAO()
        activityDAO.save(activity1)
        activityDAO.save(activity2)
        activityDAO.save(activity3)
        return activityDAO
    }
}