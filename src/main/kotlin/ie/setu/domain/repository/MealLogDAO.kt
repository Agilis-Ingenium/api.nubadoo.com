package ie.setu.domain.repository

import ie.setu.domain.MealLog
import ie.setu.domain.User
import ie.setu.domain.db.MealLogs
import ie.setu.domain.db.Users
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ie.setu.utils.mapToMealLog
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.insert

/**
 * Data Access Object (DAO) for managing meal log-related data in the Health Tracker app.
 *
 * This class provides methods to retrieve, create, update, and delete meal logs in the database.
 *
 * @author Warren Byron (adapted from SETU Msc Computing Agile Dev course content).
 */

class MealLogDAO {

    /**
     * Retrieves a list of all meal logs from the database.
     *
     * @return An ArrayList of [MealLog] objects representing all meal logs.
     */
    fun getAll(): ArrayList<MealLog> {
        val mealLogList: ArrayList<MealLog> = arrayListOf()
        transaction {
            MealLogs.selectAll().map {
                mealLogList.add(mapToMealLog(it)) }
        }
        return mealLogList
    }

    /**
     * Saves a meal log to the database.
     *
     * @param mealLog The [MealLog] object to be saved.
     * @return The unique identifier (mealLogId) of the newly saved user.
     */
    fun save(mealLog: MealLog) : Int? {
        return transaction {
            MealLogs.insert {
                it[logId] = mealLog.logId
                it[userId] = mealLog.userId
                it[mealTime] = mealLog.mealTime
                it[totalCalories] = mealLog.totalCalories
            } get MealLogs.logId
        }
    }
}