package ie.setu.repository

import ie.setu.domain.FoodItem
import ie.setu.domain.db.FoodItems
import ie.setu.domain.repository.FoodItemDAO
import ie.setu.helpers.fooditems
import org.junit.jupiter.api.Assertions.assertEquals
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

//retrieving some test data from Fixtures
val fooditem1 = fooditems.get(0)
val fooditem2 = fooditems.get(1)
val fooditem3 = fooditems.get(2)

class FoodItemDAOTest {

    companion object {

        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    @Nested
    inner class ReadFoodItem {
        @Test
        fun `getting all food items from a populated table returns all rows`() {
            transaction {

                //Arrange - create and populate table with three users
                val foodItemDAO = populateFoodItemTable()

                //Act & Assert
                assertEquals(3, foodItemDAO.getAll().size)
            }
        }

        @Test
        fun `get food item by id that doesn't exist, results in no food item returned`() {
            transaction {

                //Arrange - create and populate table with three users
                val foodItemDAO = populateFoodItemTable()

                //Act & Assert
                assertEquals(null, foodItemDAO.findById(4))
            }
        }

        @Test
        fun `get food item by id that exists, results in a correct food item returned`() {
            transaction {
                //Arrange - create and populate table with three food items
                SchemaUtils.create(FoodItems)
                val foodItemDAO = FoodItemDAO()
                foodItemDAO.save(fooditem1)
                foodItemDAO.save(fooditem2)
                foodItemDAO.save(fooditem3)

                //Act & Assert
                assertEquals(null, foodItemDAO.findById(4))
            }

        }
    }

    @Nested
    inner class CreateFoodItems {
        @Test
        fun `multiple food items added to table can be retrieved successfully`() {
            transaction {

                //Arrange - create and populate table with three users
                val foodItemDAO = populateFoodItemTable()

                //Act & Assert
                assertEquals(3, foodItemDAO.getAll().size)
                assertEquals(fooditem1, foodItemDAO.findById(fooditem1.foodItemId))
                assertEquals(fooditem2, foodItemDAO.findById(fooditem2.foodItemId))
                assertEquals(fooditem3, foodItemDAO.findById(fooditem3.foodItemId))
            }
        }
    }

    @Nested
    inner class DeleteFoodItemss {
        @Test
        fun `deleting a non-existant food item in table results in no deletion`() {
            transaction {

                //Arrange - create and populate table with three users
                val foodItemDAO = populateFoodItemTable()

                //Act & Assert
                assertEquals(3, foodItemDAO.getAll().size)
                foodItemDAO.delete(4)
                assertEquals(3, foodItemDAO.getAll().size)
            }
        }

        @Test
        fun `deleting an existing food item in table results in record being deleted`() {
            transaction {

                //Arrange - create and populate table with three users
                val foodItemDAO = populateFoodItemTable()

                //Act & Assert
                assertEquals(3, foodItemDAO.getAll().size)
                foodItemDAO.delete(fooditem3.foodItemId)
                assertEquals(2, foodItemDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class UpdateFoodItems {

        @Test
        fun `updating existing food item in table results in successful update`() {
            transaction {

                //Arrange - create and populate table with three users
                val foodItemDAO = populateFoodItemTable()

                //Act & Assert
                val foodItem3Updated = FoodItem(3, "Pumpkin", 105, 27.0, 1.3, 0.4, "Vitamin C, Vitamin B6", "Potassium, Manganese")
                foodItemDAO.update(fooditem3.foodItemId, foodItem3Updated)

                assertEquals(foodItem3Updated, foodItemDAO.findById(3))
            }
        }

        @Test
        fun `updating non-existant food item in table results in no updates`() {
            transaction {

                //Arrange - create and populate table with three users
                val foodItemDAO = populateFoodItemTable()

                //Act & Assert
                val foodItem4Updated = FoodItem(10, "Pumpkin", 105, 27.0, 1.3, 0.4, "Vitamin C, Vitamin B6", "Potassium, Manganese")
                foodItemDAO.update(4, foodItem4Updated)
                assertEquals(null, foodItemDAO.findById(4))
                assertEquals(3, foodItemDAO.getAll().size)
            }
        }
    }
    @Test
    fun `get all food items over empty table returns none`() {
        transaction {

            //Arrange - create and setup foodItemDAO object
            SchemaUtils.create(FoodItems)
            val foodItemDAO = FoodItemDAO()

            //Act & Assert
            assertEquals(0, foodItemDAO.getAll().size)
        }
    }

    //@Test
    //fun `get food item by email that doesn't exist, results in no food item returned`() {
    //    transaction {

            //Arrange - create and populate table with three food items
    //        val foodItemDAO = populateFoodItemTable()

            //Act & Assert
    //        assertEquals(null, foodItemDAO.findById(nonExistingEmail))
     //   }
    //}

    @Test
    //fun `get food item by email that exists, results in correct food item returned`() {
    //    transaction {

            //Arrange - create and populate table with three users
    //        val foodItemDAO = populateFoodItemTable()

            //Act & Assert
    //        assertEquals(fooditem2, foodItemDAO.findByName(fooditem2.foodItemId))
    //    }
    //}

    internal fun populateFoodItemTable(): FoodItemDAO{
        SchemaUtils.create(FoodItems)
        val foodItemDAO = FoodItemDAO()
        foodItemDAO.save(fooditem1)
        foodItemDAO.save(fooditem2)
        foodItemDAO.save(fooditem3)
        return foodItemDAO
    }
}