package ie.setu.controllers

import ie.setu.config.DbConfig
import ie.setu.controllers.FitnessGoalController.deleteFitnessGoal
import ie.setu.domain.User
import ie.setu.helpers.*
import ie.setu.utils.jsonToObject
import org.junit.jupiter.api.TestInstance
import kong.unirest.Unirest
import org.checkerframework.checker.signature.qual.ClassGetSimpleName
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import ie.setu.domain.FitnessGoal
import ie.setu.helpers.*
import ie.setu.utils.jsonToObject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Assertions.assertEquals
import ie.setu.helpers.ServerContainer

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FitnessGoalControllerTest {

    private val db = DbConfig().getDbConnection()
    private val app = ServerContainer.instance
    private val origin = "http://localhost:" + app.port() + "/v1"

    @Nested
    inner class ReadFitnessGoals {
        //getAllFitnessGoals
        @Test
        fun `get all fitness goals back from api returns 200 or error (4xx or 5xx) response`() {

            val response = Unirest.get(origin + "/fitness-goals").asString()
            assertEquals(200, response.status)

            println("Success : " + response.isSuccess)
            println("Status code : " + response.status)
            println("Body : " + response.body)
        }

        //getFitnessGoalByFitnessGoalId (200)
        @Test
        fun `get fitness goal by fitness goal id back from the api return 200 response`() {
            val response = Unirest.get(origin + "/fitness-goals/1").asString()
            assertEquals(200, response.status)

            println("Success : " + response.isSuccess)
            println("Status code : " + response.status)
            println("Body : " + response.body)
        }

        //getFitnessGoalByFitnessGoalId (400)
        @Test
        fun `get fitness goal by by non existant fitness goal id back from the api return 4xx response`() {
            val response = Unirest.get(origin + "/fitness-goals/-1").asString()
            assertEquals(404, response.status)

            println("Success : " + response.isSuccess)
            println("Status code : " + response.status)
            println("Body : " + response.body)
        }
    }

    @Nested
    inner class CreateFitnessGoals {
        @Test
        fun `add a fitness goal when a user exists for it, returns a 200 response`() {

            val addedUser: User = jsonToObject(addUser(validName, validEmail).body.toString())

            val addFitnessGoalResponse = addFitnessGoal(

                fitnessgoals.get(0).goalId,
                addedUser.userId,
                fitnessgoals.get(0).goalType,
                fitnessgoals.get(0).targetValue,
                fitnessgoals.get(0).targetDate,
                fitnessgoals.get(0).achieved,
            )

            assertEquals(200, addFitnessGoalResponse.status)

            deleteUser(addedUser.userId)
        }
    }

    @Nested
    inner class DeleteFitnessGoals {

        @Test
        fun `deleting a fitness goal when it doesn't exist, returns a 404 response`() {

            val deleteGoalResponse = Unirest.delete(origin + "/fitness-goals/1000000").asString()

            assertEquals(404, deleteGoalResponse.status)

            println("Success : " + deleteGoalResponse.isSuccess)
            println("Status code : " + deleteGoalResponse.status)
            println("Body : " + deleteGoalResponse.body)
        }
    }
}