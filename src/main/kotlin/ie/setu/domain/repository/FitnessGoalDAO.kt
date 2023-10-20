package ie.setu.domain.repository

import ie.setu.domain.FitnessGoal
import ie.setu.domain.db.FitnessGoals
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ie.setu.utils.mapToFitnessGoal

class FitnessGoalDAO {

    fun getAll(): ArrayList<FitnessGoal> {
        val activityList: ArrayList<FitnessGoal> = arrayListOf()
        transaction {
            FitnessGoals.selectAll().map {
                activityList.add(mapToFitnessGoal(it)) }
        }
        return activityList
    }
}