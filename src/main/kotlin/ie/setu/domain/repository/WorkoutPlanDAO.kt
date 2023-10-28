package ie.setu.domain.repository

import ie.setu.domain.WorkoutPlan
import ie.setu.domain.db.WorkoutPlans
import org.jetbrains.exposed.sql.transactions.transaction
import ie.setu.utils.mapToWorkoutPlan
import org.jetbrains.exposed.sql.*

class WorkoutPlanDAO {

    fun getAll(): ArrayList<WorkoutPlan> {
        val workoutPlanList: ArrayList<WorkoutPlan> = arrayListOf()
        transaction {
            WorkoutPlans.selectAll().map {
                workoutPlanList.add(mapToWorkoutPlan(it)) }
        }
        return workoutPlanList
    }

    fun findById(id: Int): WorkoutPlan?{
        return transaction {
            WorkoutPlans.select() {
                WorkoutPlans.planId eq id}
                .map{ mapToWorkoutPlan(it) }
                .firstOrNull()
        }
    }

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

    fun delete(workoutPlanId: Int):Int{
        return transaction{
            WorkoutPlans.deleteWhere{
                WorkoutPlans.planId eq workoutPlanId
            }
        }
    }
}