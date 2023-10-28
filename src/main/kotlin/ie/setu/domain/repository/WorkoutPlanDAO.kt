package ie.setu.domain.repository

import ie.setu.domain.WorkoutPlan
import ie.setu.domain.db.WorkoutPlans
import org.jetbrains.exposed.sql.transactions.transaction
import ie.setu.utils.mapToWorkoutPlan
import org.jetbrains.exposed.sql.*

/**
 * Data Access Object (DAO) for managing workout plan-related data in the Health Tracker app.
 *
 * This class provides methods to retrieve, create, update, and delete workout plans in the database.
 *
 * @author Your Name (or the name of the author/contributor).
 */
class WorkoutPlanDAO {

    /**
     * Retrieves a list of all workout plans from the database.
     *
     * @return An ArrayList of [WorkoutPlan] objects representing all workout plans.
     */
    fun getAll(): ArrayList<WorkoutPlan> {
        val workoutPlanList: ArrayList<WorkoutPlan> = arrayListOf()
        transaction {
            WorkoutPlans.selectAll().map {
                workoutPlanList.add(mapToWorkoutPlan(it)) }
        }
        return workoutPlanList
    }

    /**
     * Retrieves a workout plan by its unique identifier.
     *
     * @param id The unique identifier of the workout plan to retrieve.
     * @return A [WorkoutPlan] object representing the workout plan with the specified ID, or null if not found.
     */
    fun findById(id: Int): WorkoutPlan?{
        return transaction {
            WorkoutPlans.select() {
                WorkoutPlans.planId eq id}
                .map{ mapToWorkoutPlan(it) }
                .firstOrNull()
        }
    }

    /**
     * Saves a new workout plan to the database.
     *
     * @param workoutPlan The [WorkoutPlan] object to be saved.
     * @return The unique identifier (planId) of the newly saved workout plan.
     */
    fun save(workoutPlan: WorkoutPlan) : Int? {
        return transaction {
            WorkoutPlans.insert {
                //it[planId] = workoutPlan.planId
                it[userId] = workoutPlan.userId
                it[planName] = workoutPlan.planName
                it[schedule] = workoutPlan.schedule
                it[planDate] = workoutPlan.planDate
            } get WorkoutPlans.planId
        }
    }

    /**
     * Updates an existing workout plan in the database.
     *
     * @param workoutPlanId The unique identifier of the workout plan to update.
     * @param workoutPlan The updated [WorkoutPlan] object.
     * @return The number of rows affected by the update operation.
     */
    fun update(workoutPlanId: Int, workoutPlan: WorkoutPlan) : Int {
        return transaction {
            WorkoutPlans.update ({
                WorkoutPlans.planId eq workoutPlanId}) {
                it[WorkoutPlans.userId] = workoutPlan.userId
                it[WorkoutPlans.planName] = workoutPlan.planName
                it[WorkoutPlans.schedule] = workoutPlan.schedule
                it[WorkoutPlans.planDate] = workoutPlan.planDate
            }
        }
    }

    /**
     * Deletes a workout plan from the database by its unique identifier.
     *
     * @param workoutPlanId The unique identifier of the workout plan to delete.
     * @return The number of rows affected by the deletion operation.
     */
    fun delete(workoutPlanId: Int):Int{
        return transaction{
            WorkoutPlans.deleteWhere{
                WorkoutPlans.planId eq workoutPlanId
            }
        }
    }
}