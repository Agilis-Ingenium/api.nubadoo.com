package ie.setu.controllers

import ie.setu.domain.User
import io.javalin.http.Context
import ie.setu.utils.jsonToObject
import ie.setu.domain.repository.WorkoutPlanDAO
import ie.setu.domain.WorkoutPlan
import io.javalin.openapi.*

/**
 * Controller for handling workout plan-related operations.
 */
object WorkoutPlanController {

    private val workoutPlanDao = WorkoutPlanDAO()

    /**
     * Gets all workout plans.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Get all workout plans",
        operationId = "getAllWorkoutPlans",
        tags = ["Workout Plan"],
        responses = [
            OpenApiResponse("200", [OpenApiContent(Array<WorkoutPlan>::class)])
                    ],
        path = "/v1/workout-plans",
        methods = [HttpMethod.GET]
    )
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
    @OpenApi(
        summary = "Get a workout plan by its workout plan ID",
        operationId = "getWorkoutPlanByWorkoutPlanId",
        tags = ["Workout Plan"],
        responses = [
            OpenApiResponse("200", [OpenApiContent(WorkoutPlan::class)]),
            OpenApiResponse("404", description = "Workout plan not found")
                    ],
        path = "/v1/workout-plans/{workout-plan-id}",
        methods = [HttpMethod.GET]
    )
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
    @OpenApi(
        summary = "Add a new workout plan",
        operationId = "addWorkoutPlan",
        tags = ["Workout Plan"],
        requestBody = OpenApiRequestBody([OpenApiContent(WorkoutPlan::class)]),
        responses = [
            OpenApiResponse("201", [OpenApiContent(Array<WorkoutPlan>::class)])
        ],
        path = "/v1/workout-plans",
        methods = [HttpMethod.POST]
    )
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
    @OpenApi(
        summary = "Delete a workout plan by its workout plan ID",
        operationId = "deleteWorkoutPlan",
        tags = ["Workout Plan"],
        responses = [
            OpenApiResponse("204", description = "Workout plan deleted"),
            OpenApiResponse("404", description = "Workout plan not found")
                    ],
        path = "/v1/workout-plans/{workout-plan-id}",
        methods = [HttpMethod.DELETE]
    )
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
    @OpenApi(
        summary = "Update a workout plan's information by its workout plan ID",
        operationId = "updateWorkoutPlan",
        tags = ["Workout Plan"],
        requestBody = OpenApiRequestBody([OpenApiContent(WorkoutPlan::class)]),
        responses = [
            OpenApiResponse("204", description = "Workout plan updated"),
            OpenApiResponse("404", description = "Workout plan not found")
                    ],
        path = "/v1/workout-plans/{workout-plan-id}",
        methods = [HttpMethod.PATCH]
    )
    fun updateWorkoutPlan(ctx: Context){
        val foundWorkoutPlan : WorkoutPlan = jsonToObject(ctx.body())
        if ((workoutPlanDao.update(workoutPlanId = ctx.pathParam("workout-plan-id").toInt(), workoutPlan=foundWorkoutPlan)) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }
}