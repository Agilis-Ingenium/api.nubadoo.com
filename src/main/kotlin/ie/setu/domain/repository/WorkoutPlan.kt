package ie.setu.domain.repository

import ie.setu.domain.WorkoutPlan
import ie.setu.domain.db.WorkoutPlans
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ie.setu.utils.mapToWorkoutPlan
import org.jetbrains.exposed.sql.select
class WorkoutPlan {

    fun getAll(): ArrayList<WorkoutPlan> {
        val activityList: ArrayList<WorkoutPlan> = arrayListOf()
        transaction {
            WorkoutPlans.selectAll().map {
                activityList.add(mapToWorkoutPlan(it)) }
        }
        return activityList
    }
}