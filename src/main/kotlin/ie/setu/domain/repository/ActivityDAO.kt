package ie.setu.domain.repository

import ie.setu.domain.Activity
import ie.setu.domain.User
import ie.setu.domain.db.Activities
import ie.setu.domain.db.FitnessGoals
import ie.setu.domain.db.Users
import org.jetbrains.exposed.sql.transactions.transaction
import ie.setu.utils.mapToActivity
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.joda.time.DateTime

/**
 * Data Access Object (DAO) for managing activity-related data in the Health Tracker app.
 *
 * This class provides methods to retrieve, create, update, and delete activities in the database.
 *
 * @author Warren Byron (adapted from SETU Msc Computing Agile Dev course content).
 */
class ActivityDAO {

    /**
     * Get all activities in the database regardless of user ID.
     * @return A list of all activities.
     */
    fun getAll(): ArrayList<Activity> {
        val activitiesList: ArrayList<Activity> = arrayListOf()
        transaction {
            Activities.selectAll().map {
                activitiesList.add(mapToActivity(it))
            }
        }
        return activitiesList
    }

    /**
     * Find a specific activity by its activity ID.
     * @param id The ID of the activity to find.
     * @return The found activity or null if not found.
     */
    fun findByActivityId(id: Int): Activity? {
        return transaction {
            Activities
                .select() { Activities.activityId eq id }
                .map { mapToActivity(it) }
                .firstOrNull()
        }
    }

    /**
     * Find all activities for a specific user ID.
     * @param userId The user ID for which to find activities.
     * @return A list of activities associated with the given user ID.
     */
    fun findByUserId(userId: Int): List<Activity> {
        return transaction {
            Activities
                .select { Activities.userId eq userId }
                .map { mapToActivity(it) }
        }
    }

    /**
     * Save an activity to the database.
     * @param activity The activity to save.
     * @return The unique identifier (activityId) of the newly saved activity.
     */
    fun save(activity: Activity): Int {
        return transaction {
            Activities.insert {
                it[distanceKM] = activity.distanceKm
                it[workoutIntensity] = activity.workoutIntensity
                it[durationMinutes] = activity.durationMinutes
                it[activityType] = activity.activityType
                it[userId] = activity.userId
                it[activityDate] = activity.activityDate
            } get Activities.activityId
        }
    }

    /**
     * Deletes an activity from the database by its unique identifier.
     *
     * @param activityId The unique identifier of the fitness goal to delete.
     * @return The number of rows affected by the deletion operation.
     */
    fun delete(activityId: Int):Int{
        return transaction{
            Activities.deleteWhere{
                Activities.activityId eq activityId
            }
        }
    }

    /**
     * Updates an existing activity in the database.
     *
     * @param activityId The unique identifier of the activity to update.
     * @param activity The updated [Activity] object.
     * @return The number of rows affected by the update operation.
     */
    fun update(activityId: Int, activity: Activity) : Int {
        return transaction {
            Activities.update ({
                Activities.activityId eq activityId}) {
                it[userId] = activity.userId
                it[activityType] = activity.activityType
                it[durationMinutes] = activity.durationMinutes
                it[distanceKM] = activity.distanceKm
                it[workoutIntensity] = activity.workoutIntensity
                //it[activityDate] = activity.activityDate
            }
        }
    }
}