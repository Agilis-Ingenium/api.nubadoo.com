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
import org.junit.jupiter.api.Assertions.assertEquals
import ie.setu.helpers.ServerContainer


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ActivityControllerTest {

    private val db = DbConfig().getDbConnection()
    private val app = ServerContainer.instance
    private val origin = "http://localhost:" + app.port()

    @Test
    fun `get all users from the database returns 200 or 404 response`() {
        val response = Unirest.get(origin + "/v1/users/").asString()
        assertEquals(200, response.status)
    }

    @Nested
    inner class CreateActivities {

        @Test
        fun `add an activity when a user exists for it, returns a 201 response`() {

            val addedUser: User = jsonToObject(addUser(validName, validEmail).body.toString())

            val addActivityResponse = addActivity(

                activities.get(0).activityId,
                activities.get(0).activityDate,
                activities.get(0).workoutIntensity,
                activities.get(0).distanceKm,
                activities.get(0).durationMinutes,
                activities.get(0).activityType,
                addedUser.userId
            )

            assertEquals(201, addActivityResponse.status)

            deleteUser(addedUser.userId)
        }

        @Test
        fun `add an activity when no user exists for it, returns a 404 response`() {

            val userId = -1
            assertEquals(404, retrieveUserById(userId).status)

            val addActivityResponse = addActivity(
                activities.get(0).activityId,
                activities.get(0).activityDate,
                activities.get(0).workoutIntensity,
                activities.get(0).distanceKm,
                activities.get(0).durationMinutes,
                activities.get(0).activityType,
                userId
            )
            assertNotEquals(201, addActivityResponse.status)
        }
    }
/*
    @Nested
    inner class ReadActivities {
           get(   "/api/users/:user-id/activities", HealthTrackerController::getActivitiesByUserId)
           get(   "/api/activities", HealthTrackerController::getAllActivities)
           get(   "/api/activities/:activity-id", HealthTrackerController::getActivitiesByActivityId)
    }

 */

    @Nested
    inner class UpdateActivities {
        //  patch( "/api/activities/:activity-id", HealthTrackerController::updateActivity)
    }

    @Nested
    inner class DeleteActivities {
        //   delete("/api/activities/:activity-id", HealthTrackerController::deleteActivityByActivityId)
        //   delete("/api/users/:user-id/activities", HealthTrackerController::deleteActivityByUserId)
    }
}

