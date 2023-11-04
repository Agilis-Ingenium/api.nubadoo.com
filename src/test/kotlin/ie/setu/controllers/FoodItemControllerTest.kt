package ie.setu.controllers

import ie.setu.config.DbConfig
import ie.setu.domain.FoodItem
import ie.setu.helpers.*
import ie.setu.utils.jsonToObject
import kong.unirest.Unirest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FoodItemControllerTest {

    private val db = DbConfig().getDbConnection()
    private val app = ServerContainer.instance
    private val origin = "http://localhost:" + app.port()

    @Nested
    inner class ReadFoodItems {
        @Test
        fun `get all food items from the database returns 200 or 404 response`() {
            val response = Unirest.get(origin + "/v1/food-items/").asString()
            if (response.status == 200) {
                val retrievedFoodItems: ArrayList<FoodItem> = jsonToObject(response.body.toString())
                assertNotEquals(0, retrievedFoodItems.size)
            }
            else {
                assertEquals(404, response.status)
            }
        }

        @Test
        fun `get food item by id when food item does not exist returns 404 response`() {

            //Arrange & Act - attempt to retrieve the non-existent food item from the database
            val retrieveResponse = retrieveFoodItemById(Integer.MIN_VALUE)

            // Assert -  verify return code
            assertEquals(404, retrieveResponse.status)
        }

        @Test
        fun `getting a food item by id when id exists, returns a 200 response`() {

            //Arrange - add the food item
            val addResponse = addFoodItem(validName)
            val addedFoodItem: FoodItem = jsonToObject(addResponse?.body.toString())

            //Assert - retrieve the added food item from the database and verify return code
            val retrieveResponse = retrieveFoodItemById(addedFoodItem.foodItemId)
            assertEquals(200, retrieveResponse.status)

            //After - restore the db to previous state by deleting the added food item
            deleteFoodItem(addedFoodItem.foodItemId)
        }
    }

    @Nested
    inner class CreateFoodItems {
        @Test
        fun `add a food item with correct details returns a 201 response`() {

            //Arrange & Act & Assert
            //    add the food item and verify return code (using fixture data)
            val addResponse = addFoodItem(validName)
            assertEquals(201, addResponse?.status)

            //Assert - retrieve the added food item from the database and verify return code
            //val retrieveResponse = retrieveFoodItemByEmail(validEmail)
            //assertEquals(200, retrieveResponse.status)

            //Assert - verify the contents of the retrieved food item
            val retrievedFoodItem: FoodItem = jsonToObject(addResponse?.body.toString())
            //assertEquals(validEmail, retrievedFoodItem.email)
            assertEquals(validName, retrievedFoodItem.name)

            //After - restore the db to previous state by deleting the added food item
            val deleteResponse = deleteFoodItem(retrievedFoodItem.foodItemId)
            assertEquals(204, deleteResponse.status)
        }
    }

    @Nested
    inner class UpdateFoodItems {
        @Test
        fun `updating a food item when it exists, returns a 204 response`() {

            //Arrange - add the food item that we plan to do an update on
            val addedResponse = addFoodItem(validName)
            val addedFoodItem: FoodItem = jsonToObject(addedResponse?.body.toString())

            //Act & Assert - update the email and name of the retrieved food item and assert 204 is returned
            assertEquals(204, updateFoodItem(addedFoodItem.foodItemId, updatedName, updatedEmail).status)

            //Act & Assert - retrieve updated food item and assert details are correct
            val updatedFoodItemResponse = retrieveFoodItemById(addedFoodItem.foodItemId)
            val updatedFoodItem: FoodItem = jsonToObject(updatedFoodItemResponse.body.toString())
            assertEquals(updatedName, updatedFoodItem.name)

            //assertEquals(updatedName, updatedFoodItem.lastName)
            //assertEquals(updatedEmail, updatedFoodItem.email)

            //After - restore the db to previous state by deleting the added food item
            deleteFoodItem(addedFoodItem.foodItemId)
        }

        @Test
        fun `updating a food item when it doesn't exist, returns a 404 response`() {

            //Act & Assert - attempt to update the email and name of food item that doesn't exist
            assertEquals(404, updateFoodItem(-1, updatedName, updatedEmail).status)
        }
    }

    @Nested
    inner class DeleteFoodItems {
        @Test
        fun `deleting a food item when it doesn't exist, returns a 404 response`() {
            //Act & Assert - attempt to delete a food item that doesn't exist
            assertEquals(404, deleteFoodItem(-1).status)
        }

        @Test
        fun `deleting a food item when it exists, returns a 204 response`() {

            //Arrange - add the food item that we plan to do a delete on
            val addedResponse = addFoodItem(validName)
            val addedFoodItem: FoodItem = jsonToObject(addedResponse?.body.toString())

            //Act & Assert - delete the added food item and assert a 204 is returned
            assertEquals(204, deleteFoodItem(addedFoodItem.foodItemId).status)

            //Act & Assert - attempt to retrieve the deleted food item --> 404 response
            assertEquals(404, retrieveFoodItemById(addedFoodItem.foodItemId).status)
        }
    }
}
