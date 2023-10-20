package ie.setu.domain.repository

import ie.setu.domain.FoodItem
import ie.setu.domain.db.FoodItems
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ie.setu.utils.mapToFoodItem

class FoodItemDAO {

    fun getAll(): ArrayList<FoodItem> {
        val foodItemsList: ArrayList<FoodItem> = arrayListOf()
        transaction {
            FoodItems.selectAll().map {
                foodItemsList.add(mapToFoodItem(it)) }
        }
        return foodItemsList
    }
}