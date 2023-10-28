package ie.setu.domain.repository

import ie.setu.domain.FoodItem
import ie.setu.domain.User
import ie.setu.domain.db.FoodItems
import ie.setu.domain.db.FoodItems.autoIncrement
import ie.setu.domain.db.FoodItems.nullable
import ie.setu.domain.db.FoodItems.primaryKey
import ie.setu.domain.db.Users

import org.jetbrains.exposed.sql.transactions.transaction
import ie.setu.utils.mapToFoodItem
import ie.setu.utils.mapToUser
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

/**
 * Data Access Object (DAO) for managing food item-related data in the Health Tracker app.
 *
 * This class provides methods to retrieve, create, update, and delete food items in the database.
 *
 * @author Warren Byron (adapted from SETU Msc Computing Agile Dev course content).
 */
class FoodItemDAO {

    /**
     * Retrieves a list of all food items from the database.
     *
     * @return An ArrayList of [FoodItem] objects representing all food items.
     */
    fun getAll(): ArrayList<FoodItem> {
        val foodItemsList: ArrayList<FoodItem> = arrayListOf()
        transaction {
            FoodItems.selectAll().map {
                foodItemsList.add(mapToFoodItem(it)) }
        }
        return foodItemsList
    }

    /**
     * Retrieves a food item by its name. (NOTE: NEEDS FIXING)
     *
     * @param name The name of the food item to retrieve.
     * @return A [FoodItem] object representing the food item with the specified name, or null if not found.
     */
    fun findByName(name: String) : FoodItem?{
        return transaction {
            FoodItems.select() {
                FoodItems.name eq name}
                .map{ mapToFoodItem(it) }
                .firstOrNull()
        }
    }

    /**
     * Retrieves a food item by its unique identifier.
     *
     * @param id The unique identifier of the food item to retrieve.
     * @return A [FoodItem] object representing the food item with the specified ID, or null if not found.
     */
    fun findById(id: Int) : FoodItem? {
        return transaction {
            FoodItems.select() {
                FoodItems.foodItemId eq id}
                .map{ mapToFoodItem(it) }
                .firstOrNull()
        }
    }

    /**
     * Saves a new food item to the database.
     *
     * @param foodItem The [FoodItem] object to be saved.
     * @return The unique identifier (foodItemId) of the newly saved food item.
     */
    fun save(foodItem: FoodItem) : Int? {
        return transaction {
            FoodItems.insert {
                it[name] = foodItem.name
                it[calories] = foodItem.calories
                it[carbohydrates] = foodItem.carbohydrates
                it[proteins] = foodItem.proteins
                it[fats] = foodItem.fats
                it[vitamins] = foodItem.vitamins
                it[minerals] = foodItem.minerals
            } get FoodItems.foodItemId
        }
    }

    /**
     * Deletes a food item from the database by its unique identifier.
     *
     * @param foodItemId The unique identifier of the food item to delete.
     * @return The number of rows affected by the deletion operation.
     */
    fun delete(foodItemId: Int) : Int {
        return transaction{
            FoodItems.deleteWhere{
                FoodItems.foodItemId eq foodItemId
            }
        }
    }

    /**
     * Updates an existing food item in the database.
     *
     * @param foodItemId The unique identifier of the food item to update.
     * @param foodItem The updated [FoodItem] object.
     * @return The number of rows affected by the update operation.
     */
    fun update(foodItemId: Int, foodItem: FoodItem) : Int {
        return transaction {
            FoodItems.update ({
                FoodItems.foodItemId eq foodItemId}) {
                it[name] = foodItem.name
                it[calories] = foodItem.calories
                it[carbohydrates] = foodItem.carbohydrates
                it[proteins] = foodItem.proteins
                it[fats] = foodItem.fats
                it[vitamins] = foodItem.vitamins
                it[minerals] = foodItem.minerals
            }
        }
    }
}