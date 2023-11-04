package ie.setu.controllers

import ie.setu.config.DbConfig
import ie.setu.domain.Activity
import ie.setu.domain.User
import ie.setu.helpers.*
import ie.setu.utils.jsonToObject
import kong.unirest.Unirest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ActivityControllerTest {

    private val db = DbConfig().getDbConnection()
    private val app = ServerContainer.instance
    private val origin = "http://localhost:" + app.port()

    @Nested
    inner class CreateActivities {

        @Test
        fun `add an activity when a user exists for it, returns a 201 response`() {

            //Arrange - add a user and an associated activity that we plan to do a delete on
            val addedUser: User = jsonToObject(addUser(validName, validEmail).body.toString())

            val addActivityResponse = addActivity(

                activities.get(0).activityId,
                activities.get(0).activityDate,
                activities.get(0).workoutIntensity,
                activities.get(0).distanceKm,
                activities.get(0).durationMinutes,
                activities.get(0).activityType,
                addedUser.userId                           // the incrementer goes up even if user deleted afterwards
            )

            assertEquals(201, addActivityResponse.status)


            //After - delete the user (Activity will cascade delete in the database)
            deleteUser(addedUser.userId)
        }
    }
}

