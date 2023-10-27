package ie.setu.domain.repository

import ie.setu.domain.FitnessGoal
import ie.setu.domain.db.FitnessGoals
import org.jetbrains.exposed.sql.transactions.transaction
import ie.setu.utils.mapToFitnessGoal
import org.jetbrains.exposed.sql.*

class FitnessGoalDAO {

    fun getAll(): ArrayList<FitnessGoal> {
        val fitnessGoalList: ArrayList<FitnessGoal> = arrayListOf()
        transaction {
            FitnessGoals.selectAll().map {
                fitnessGoalList.add(mapToFitnessGoal(it)) }
        }
        return fitnessGoalList
    }

    fun findById(id: Int): FitnessGoal?{
        return transaction {
            FitnessGoals.select() {
                FitnessGoals.goalId eq id}
                .map{ mapToFitnessGoal(it) }
                .firstOrNull()
        }
    }

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

    fun delete(fitnessGoalId: Int):Int{
        return transaction{
            FitnessGoals.deleteWhere{
                FitnessGoals.goalId eq fitnessGoalId
            }
        }
    }

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