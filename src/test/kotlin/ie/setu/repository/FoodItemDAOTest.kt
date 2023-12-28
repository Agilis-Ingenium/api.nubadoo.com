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

val fooditem1 = fooditems.get(0)
val fooditem2 = fooditems.get(1)
val fooditem3 = fooditems.get(2)

class FoodItemDAOTest {

    companion object {

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

                val foodItemDAO = populateFoodItemTable()

                println(foodItemDAO.getAll())

                assertEquals(3, foodItemDAO.getAll().size)
            }
        }

        @Test
        fun `get food item by id that doesn't exist, results in no food item returned`() {
            transaction {

                val foodItemDAO = populateFoodItemTable()

                println(foodItemDAO.findById(4))

                assertEquals(null, foodItemDAO.findById(4))
            }
        }

        @Test
        fun `get food item by id that exists, results in a correct food item returned`() {
            transaction {

                val foodItemDAO = populateFoodItemTable()

                println(fooditem3)

                assertEquals(fooditem3, foodItemDAO.findById(3))
            }
        }

        @Test
        fun `get all food items over empty table returns none`() {
            transaction {

                SchemaUtils.create(FoodItems)
                val foodItemDAO = FoodItemDAO()

                println(foodItemDAO.getAll())

                assertEquals(0, foodItemDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class CreateFoodItems {
        @Test
        fun `multiple food items added to table can be retrieved successfully`() {
            transaction {

                val foodItemDAO = populateFoodItemTable()

                println(foodItemDAO.getAll())

                assertEquals(3, foodItemDAO.getAll().size)
                assertEquals(fooditem1, foodItemDAO.findById(fitnessgoal1.goalId))
                assertEquals(fooditem2, foodItemDAO.findById(fitnessgoal2.goalId))
                assertEquals(fooditem3, foodItemDAO.findById(fitnessgoal3.goalId))
            }
        }
    }

    @Nested
    inner class DeleteFoodItemss {
        @Test
        fun `deleting a non-existant food item in table results in no deletion`() {
            transaction {

                val foodItemDAO = populateFoodItemTable()

                println(foodItemDAO.getAll())

                assertEquals(3, foodItemDAO.getAll().size)
                foodItemDAO.delete(4)
                assertEquals(3, foodItemDAO.getAll().size)
            }
        }

        @Test
        fun `deleting an existing food item in table results in record being deleted`() {
            transaction {

                val foodItemDAO = populateFoodItemTable()

                println(foodItemDAO.getAll())

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

                val foodItemDAO = populateFoodItemTable()

                val foodItem3Updated = FoodItem(
                    3,
                    "Pumpkin",
                    105,
                    27.0,
                    1.3,
                    0.4,
                    "Vitamin C, Vitamin B6",
                    "Potassium, Manganese")
                foodItemDAO.update(fooditem3.foodItemId, foodItem3Updated)

                val toCompare = foodItemDAO.findById(3)

                println(toCompare)
                println("---")
                println(foodItem3Updated)

                assertEquals(foodItem3Updated, toCompare)
            }
        }

        @Test
        fun `updating non-existant food item in table results in no updates`() {
            transaction {

                val foodItemDAO = populateFoodItemTable()

                val foodItem4Updated = FoodItem(10,
                    "Pumpkin",
                    105,
                    27.0,
                    1.3,
                    0.4,
                    "Vitamin C, Vitamin B6",
                    "Potassium, Manganese")

                println(foodItemDAO.findById(4))
                println("---")
                println(foodItemDAO.getAll().size)

                foodItemDAO.update(4, foodItem4Updated)

                assertEquals(null, foodItemDAO.findById(4))
                assertEquals(3, foodItemDAO.getAll().size)
            }
        }
    }

    internal fun populateFoodItemTable(): FoodItemDAO{
        SchemaUtils.create(FoodItems)
        val foodItemDAO = FoodItemDAO()
        foodItemDAO.save(fooditem1)
        foodItemDAO.save(fooditem2)
        foodItemDAO.save(fooditem3)
        return foodItemDAO
    }
}