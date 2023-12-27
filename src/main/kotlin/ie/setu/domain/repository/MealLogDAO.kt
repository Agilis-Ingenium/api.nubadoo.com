package ie.setu.domain.repository

import ie.setu.domain.MealLog
import ie.setu.domain.db.MealLogs
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ie.setu.utils.mapToMealLog

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
     * @throws SQLException if an error occurs while interacting with the database.
     */
    fun getAll(): ArrayList<MealLog> {
        val mealLogList: ArrayList<MealLog> = arrayListOf()
        transaction {
            MealLogs.selectAll().map {
                mealLogList.add(mapToMealLog(it)) }
        }
        return mealLogList
    }
}