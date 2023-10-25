package ie.setu.controllers

import io.javalin.http.Context
import ie.setu.domain.repository.WorkoutPlanDAO

object WorkoutPlanController {

    private val workoutPlanDao = WorkoutPlanDAO()

    fun getAllWorkoutPlans(ctx: Context) {
        ctx.json(workoutPlanDao.getAll())
    }
}