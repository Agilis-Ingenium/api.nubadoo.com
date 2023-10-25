package ie.setu.domain.repository

import ie.setu.domain.MealLog
import ie.setu.domain.db.MealLogs
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ie.setu.utils.mapToMealLog

class MealLogDAO {

    fun getAll(): ArrayList<MealLog> {
        val mealLogList: ArrayList<MealLog> = arrayListOf()
        transaction {
            MealLogs.selectAll().map {
                mealLogList.add(mapToMealLog(it)) }
        }
        return mealLogList
    }
}