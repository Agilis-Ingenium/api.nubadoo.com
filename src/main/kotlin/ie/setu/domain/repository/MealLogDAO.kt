package ie.setu.domain.repository

import ie.setu.domain.MealLog
import ie.setu.domain.db.MealLogs
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ie.setu.utils.mapToMealLog

class MealLogDAO {

    fun getAll(): ArrayList<MealLog> {
        val activityList: ArrayList<MealLog> = arrayListOf()
        transaction {
            MealLogs.selectAll().map {
                activityList.add(mapToMealLog(it)) }
        }
        return activityList
    }
}