package ie.setu.controllers

import ie.setu.domain.FitnessGoal
import ie.setu.domain.repository.FitnessGoalDAO
import ie.setu.utils.jsonToObject
import io.javalin.http.Context
import io.javalin.openapi.*

/**
 * Controller for managing fitness goals in the Health Tracker API.
 * This controller provides endpoints to retrieve, create, update, and delete fitness goals.
 */
object FitnessGoalController {

    private val fitnessGoalDao = FitnessGoalDAO()

    /**
     * Get all fitness goals.
     *
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Get all fitness goals",
        operationId = "getAllFitnessGoals",
        tags = ["Fitness"],
        responses = [
            OpenApiResponse("200")
        ],
        path = "/v1/fitness-goals",
        methods = [HttpMethod.GET]
    )
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

    /**
     * Get a fitness goal by its ID.
     *
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Get fitness goal by ID",
        operationId = "getFitnessGoalByFitnessGoalId",
        tags = ["Fitness"],
        responses = [
            OpenApiResponse("200", [OpenApiContent(FitnessGoal::class)]),
            OpenApiResponse("404", [OpenApiContent(String::class)])
        ],
        path = "/v1/fitness-goals/{fitness-goal-id}",
        methods = [HttpMethod.GET]
    )
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

    /**
     * Add a new fitness goal.
     *
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Add a new fitness goal",
        operationId = "addFitnessGoal",
        tags = ["Fitness"],
        requestBody = OpenApiRequestBody([OpenApiContent(FitnessGoal::class)]),
        responses = [
            OpenApiResponse("200", [OpenApiContent(FitnessGoal::class)]),
            OpenApiResponse("400", [OpenApiContent(String::class)])
        ],
        path = "/v1/fitness-goals",
        methods = [HttpMethod.POST]
    )
    fun addFitnessGoal(ctx: Context) {
        val fitnessGoal : FitnessGoal = jsonToObject(ctx.body())
        val goalId = fitnessGoalDao.save(fitnessGoal)
        if (goalId != null) {
            fitnessGoal.goalId = goalId
            ctx.json(fitnessGoal)
            ctx.status(201)
        }
    }

    /**
     * Delete a fitness goal by its ID.
     *
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Delete fitness goal by ID",
        operationId = "deleteFitnessGoal",
        tags = ["Fitness"],
        responses = [
            OpenApiResponse("200", [OpenApiContent(FitnessGoal::class)]),
            OpenApiResponse("404", [OpenApiContent(String::class)])
        ],
        path = "/v1/fitness-goals/{fitness-goal-id}",
        methods = [HttpMethod.DELETE]
    )
    fun deleteFitnessGoal(ctx: Context){
        if (fitnessGoalDao.delete(ctx.pathParam("fitness-goal-id").toInt()) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }

    /**
     * Update an existing fitness goal.
     *
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Update fitness goal by ID",
        operationId = "updateFitnessGoal",
        tags = ["Fitness"],
        responses = [
            OpenApiResponse("204", [OpenApiContent(FitnessGoal::class)]),
            OpenApiResponse("404", [OpenApiContent(String::class)])
        ],
        path = "/v1/fitness-goals/{fitness-goal-id}",
        methods = [HttpMethod.PATCH],
        requestBody = OpenApiRequestBody([OpenApiContent(FitnessGoal::class)])
    )
    fun updateFitnessGoal(ctx: Context){
        val foundFitnessGoal : FitnessGoal = jsonToObject(ctx.body())
        if ((fitnessGoalDao.update(fitnessGoalId = ctx.pathParam("fitness-goal-id").toInt(), fitnessGoal=foundFitnessGoal)) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }
}