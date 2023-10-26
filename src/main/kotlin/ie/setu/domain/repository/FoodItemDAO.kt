package ie.setu.domain.repository

import ie.setu.domain.FoodItem
import ie.setu.domain.db.FoodItems

import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ie.setu.utils.mapToFoodItem
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select

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
}