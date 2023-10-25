package ie.setu.controllers

import ie.setu.domain.repository.FitnessGoalDAO
import io.javalin.http.Context

object FitnessGoalController {

    private val fitnessGoalDao = FitnessGoalDAO()

    fun getAllFitnessGoals(ctx: Context) {
        ctx.json(fitnessGoalDao.getAll())
    }

}