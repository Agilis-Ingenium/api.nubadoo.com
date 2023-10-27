package ie.setu.controllers

import ie.setu.domain.FitnessGoal
import ie.setu.domain.repository.FitnessGoalDAO
import ie.setu.utils.jsonToObject
import io.javalin.http.Context

object FitnessGoalController {

    private val fitnessGoalDao = FitnessGoalDAO()

    fun getAllFitnessGoals(ctx: Context) {
        val fitnessGoals = fitnessGoalDao.getAll()
        if (fitnessGoals.size != 0) {
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
        ctx.json(fitnessGoals)
    }

    fun getFitnessGoalByFitnessGoalId(ctx: Context) {
        val fitnessGoal = fitnessGoalDao.findById(ctx.pathParam("fitness-goal-id").toInt())
        if (fitnessGoal != null) {
            ctx.json(fitnessGoal)
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
    }

    fun addFitnessGoal(ctx: Context) {
        val fitnessGoal : FitnessGoal = jsonToObject(ctx.body())
        val fitnessGoalId = fitnessGoalDao.save(fitnessGoal)
        if (fitnessGoalId != null) {
            fitnessGoal.goalId = fitnessGoalId
            ctx.json(fitnessGoal)
            ctx.status(201)
        }
    }

    fun deleteFitnessGoal(ctx: Context){
        if (fitnessGoalDao.delete(ctx.pathParam("fitness-goal-id").toInt()) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }

    fun updateFitnessGoal(ctx: Context){
        val foundFitnessGoal : FitnessGoal = jsonToObject(ctx.body())
        if ((fitnessGoalDao.update(fitnessGoalId = ctx.pathParam("fitness-goal-id").toInt(), fitnessGoal=foundFitnessGoal)) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }
}




