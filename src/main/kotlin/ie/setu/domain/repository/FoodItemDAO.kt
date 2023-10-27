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

class FoodItemDAO {

    fun getAll(): ArrayList<FoodItem> {
        val foodItemsList: ArrayList<FoodItem> = arrayListOf()
        transaction {
            FoodItems.selectAll().map {
                foodItemsList.add(mapToFoodItem(it)) }
        }
        return foodItemsList
    }

    fun findByName(name: String) : FoodItem?{
        return transaction {
            FoodItems.select() {
                FoodItems.name eq name}
                .map{ mapToFoodItem(it) }
                .firstOrNull()
        }
    }

    fun findById(id: Int) : FoodItem? {
        return transaction {
            FoodItems.select() {
                FoodItems.foodItemId eq id}
                .map{ mapToFoodItem(it) }
                .firstOrNull()
        }
    }

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

    fun delete(foodItemId: Int) : Int {
        return transaction{
            FoodItems.deleteWhere{
                FoodItems.foodItemId eq foodItemId
            }
        }
    }

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