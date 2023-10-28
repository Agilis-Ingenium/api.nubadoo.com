package ie.setu.controllers

import io.javalin.http.Context
import ie.setu.utils.jsonToObject
import ie.setu.domain.repository.WorkoutPlanDAO
import ie.setu.domain.WorkoutPlan

/**
 * Controller for handling workout plan-related operations.
 */
object WorkoutPlanController {

    private val workoutPlanDao = WorkoutPlanDAO()

    /**
     * Gets all workout plans.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    fun getAllWorkoutPlans(ctx: Context) {
        val workoutPlans = workoutPlanDao.getAll()
        if (workoutPlans.size != 0) {
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
        ctx.json(workoutPlans)
    }

    /**
     * Gets a workout plan by its workout plan ID.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    fun getWorkoutPlanByWorkoutPlanId(ctx: Context) {
        val workoutPlan = workoutPlanDao.findById(ctx.pathParam("workout-plan-id").toInt())
        if (workoutPlan != null) {
            ctx.json(workoutPlan)
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
    }

    /**
     * Adds a new workout plan.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    fun addWorkoutPlan(ctx: Context) {
        val workoutPlan : WorkoutPlan = jsonToObject(ctx.body())
        val workoutPlanId = workoutPlanDao.save(workoutPlan)
        if (workoutPlanId != null) {
            workoutPlan.planId = workoutPlanId
            ctx.json(workoutPlan)
            ctx.status(201)
        }
    }

    /**
     * Deletes a workout plan by its workout plan ID.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    fun deleteWorkoutPlan(ctx: Context){
        if (workoutPlanDao.delete(ctx.pathParam("workout-plan-id").toInt()) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }

    /**
     * Updates a workout plan's information by its workout plan ID.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    fun updateWorkoutPlan(ctx: Context){
        val foundWorkoutPlan : WorkoutPlan = jsonToObject(ctx.body())
        if ((workoutPlanDao.update(workoutPlanId = ctx.pathParam("workout-plan-id").toInt(), workoutPlan=foundWorkoutPlan)) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }
}