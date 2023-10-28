package ie.setu.domain.repository

import ie.setu.domain.FitnessGoal
import ie.setu.domain.db.FitnessGoals
import org.jetbrains.exposed.sql.transactions.transaction
import ie.setu.utils.mapToFitnessGoal
import org.jetbrains.exposed.sql.*

/**
 * Data Access Object (DAO) for managing fitness goal-related data in the Health Tracker app.
 *
 * This class provides methods to retrieve, create, update, and delete fitness goals in the database.
 *
 * @author Warren Byron (adapted from SETU Msc Computing Agile Dev course content).
 */
class FitnessGoalDAO {

    /**
     * Retrieves a list of all fitness goals from the database.
     *
     * @return An ArrayList of [FitnessGoal] objects representing all fitness goals.
     */
    fun getAll(): ArrayList<FitnessGoal> {
        val fitnessGoalList: ArrayList<FitnessGoal> = arrayListOf()
        transaction {
            FitnessGoals.selectAll().map {
                fitnessGoalList.add(mapToFitnessGoal(it)) }
        }
        return fitnessGoalList
    }

    /**
     * Retrieves a fitness goal by its unique identifier.
     *
     * @param id The unique identifier of the fitness goal to retrieve.
     * @return A [FitnessGoal] object representing the fitness goal with the specified ID, or null if not found.
     */
    fun findById(id: Int): FitnessGoal?{
        return transaction {
            FitnessGoals.select() {
                FitnessGoals.goalId eq id}
                .map{ mapToFitnessGoal(it) }
                .firstOrNull()
        }
    }

    /**
     * Saves a new fitness goal to the database.
     *
     * @param fitnessGoal The [FitnessGoal] object to be saved.
     * @return The unique identifier (goalId) of the newly saved fitness goal.
     */
    fun save(fitnessGoal: FitnessGoal) : Int?{
        return transaction {
            FitnessGoals.insert {
                it[goalId] = fitnessGoal.goalId
                it[userId] = fitnessGoal.userId
                it[goalType] = fitnessGoal.goalType
                it[targetValue] = fitnessGoal.targetValue
                it[targetDate] = fitnessGoal.targetDate
            } get FitnessGoals.goalId
        }
    }

    /**
     * Deletes a fitness goal from the database by its unique identifier.
     *
     * @param fitnessGoalId The unique identifier of the fitness goal to delete.
     * @return The number of rows affected by the deletion operation.
     */
    fun delete(fitnessGoalId: Int):Int{
        return transaction{
            FitnessGoals.deleteWhere{
                FitnessGoals.goalId eq fitnessGoalId
            }
        }
    }

    /**
     * Updates an existing fitness goal in the database.
     *
     * @param fitnessGoalId The unique identifier of the fitness goal to update.
     * @param fitnessGoal The updated [FitnessGoal] object.
     * @return The number of rows affected by the update operation.
     */
    fun update(fitnessGoalId: Int, fitnessGoal: FitnessGoal): Int{
        return transaction {
            FitnessGoals.update ({
                FitnessGoals.goalId eq fitnessGoalId}) {
                //it[goalId] = fitnessGoal.goalId
                it[userId] = fitnessGoal.userId
                it[goalType] = fitnessGoal.goalType
                it[targetValue] = fitnessGoal.targetValue
                it[targetDate] = fitnessGoal.targetDate
            }
        }
    }
}