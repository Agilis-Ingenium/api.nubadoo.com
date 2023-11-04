package ie.setu.domain.repository

import ie.setu.domain.MealLogFoodItem
import ie.setu.domain.db.MealLogFoodItems
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ie.setu.utils.mapToMealLogFoodItem

class MealLogFoodItemDAO {

    fun getAll(): ArrayList<MealLogFoodItem> {
        val activityList: ArrayList<MealLogFoodItem> = arrayListOf()
        transaction {
            MealLogFoodItems.selectAll().map {
                activityList.add(mapToMealLogFoodItem(it)) }
        }
        return activityList
    }
}