package ie.setu.controllers

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.javalin.http.Context
import ie.setu.domain.Activity
import ie.setu.domain.FitnessGoal
import ie.setu.domain.User
import ie.setu.domain.repository.ActivityDAO
import ie.setu.domain.repository.UserDAO
import ie.setu.utils.jsonToObject
import io.javalin.openapi.*

/**
 * Controller for managing activity-related operations in the Health Tracker app.
 * This controller provides endpoints to retrieve and create activities.
 */
object ActivityController {

    private val activityDao = ActivityDAO()
    private val userDao = UserDAO()

    /**
     * Retrieves a list of all activities and serializes them to JSON.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Get all activities",
        operationId = "getAllActivities",
        tags = ["Activity"],
        responses = [
            OpenApiResponse("200", [OpenApiContent(Array<Activity>::class)]),
            OpenApiResponse("404", [OpenApiContent(String::class)])
        ],
        path = "/v1/activities",
        methods = [HttpMethod.GET]
    )
    fun getAllActivities(ctx: Context) {
        //mapper handles the deserialization of Joda date into a String.
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        ctx.json(mapper.writeValueAsString(activityDao.getAll()))
    }

    /**
     * Retrieves activities by user ID and serializes them to JSON.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Get activities by user ID",
        operationId = "getActivitiesByUserId",
        tags = ["Activity"],
        responses = [
            OpenApiResponse("200", [OpenApiContent(Array<Activity>::class)]),
            OpenApiResponse("404", [OpenApiContent(String::class)])
        ],
        path = "/v1/activities/user/{user-id}",
        methods = [HttpMethod.GET]
    )
    fun getActivitiesByUserId(ctx: Context) {
        if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val activities = activityDao.findByUserId(ctx.pathParam("user-id").toInt())
            if (activities.isNotEmpty()) {
                //mapper handles the deserialization of Joda date into a String.
                val mapper = jacksonObjectMapper()
                    .registerModule(JodaModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                ctx.json(mapper.writeValueAsString(activities))
            }
        }
    }

    // Can't get this one to compile!!
    // Revert to the other save + addX function
    // Not sure if this is a better function???


    //fun addActivity(ctx: Context) {
    //mapper handles the serialisation of Joda date into a String.
    //    val mapper = jacksonObjectMapper()
    //        .registerModule(JodaModule())
    //        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    //    val activity = mapper.readValue<Activity>(ctx.body())
    //    activityDao.save(activity)
    //    ctx.json(activity)
    //}

    /**
     * Adds a new activity and returns it as JSON.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Add a new activity",
        operationId = "addActivity",
        tags = ["Activity"],
        requestBody = OpenApiRequestBody([OpenApiContent(Activity::class)]),
        responses = [
            OpenApiResponse("201", [OpenApiContent(Activity::class)]),
            OpenApiResponse("400", [OpenApiContent(String::class)])
        ],
        path = "/v1/activities",
        methods = [HttpMethod.POST]
    )
    fun addActivity(ctx: Context) {
        val activity: Activity = jsonToObject(ctx.body())
        val activityId = activityDao.save(activity)
        if (activityId != null) {
            activity.activityId = activityId
            ctx.json(activity)
            ctx.status(201)
        }
    }

    // LAB 6 - EXERCISES

    /**
     * Deletes activities by user ID.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Delete activities by user ID",
        operationId = "deleteActivitiesByUserId",
        tags = ["Activity"],
        responses = [
            OpenApiResponse("204", [OpenApiContent(Array<Activity>::class)]),
            OpenApiResponse("404", [OpenApiContent(String::class)])
        ],
        path = "/v1/activities/user/{user-id}",
        methods = [HttpMethod.DELETE]
    )
    fun deleteActivitiesByUserId(ctx: Context) {
        if (ActivityController.userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val activities = ActivityController.activityDao.findByUserId(ctx.pathParam("user-id").toInt())
            if (activities.isNotEmpty()) {
                activities.forEach {
                    activityDao.delete(it.activityId)
                }
            }
            else
                ctx.status(404)
        }
    }

    /**
     * Deletes an activity by its activity ID.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Delete an activity by its activity ID",
        operationId = "deleteActivity",
        tags = ["Activity"],
        responses = [
            OpenApiResponse("204", description = "User deleted"),
            OpenApiResponse("404", description = "User not found")
        ],
        path = "/v1/activities/{activity-id}",
        methods = [HttpMethod.DELETE]
    )
    fun deleteActivity(ctx: Context){
        if (ActivityController.activityDao.delete(ctx.pathParam("activity-id").toInt()) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }

    /**
     * Updates an activity's information by the activity ID.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Update an activity's information by the activity ID",
        operationId = "updateActivity",
        tags = ["Activity"],
        requestBody = OpenApiRequestBody([OpenApiContent(Activity::class)]),
        responses = [
            OpenApiResponse("204", description = "User updated"),
            OpenApiResponse("404", description = "User not found")
        ],
        path = "/v1/activities/{activity-id}",
        methods = [HttpMethod.PATCH]
    )
    fun updateActivity(ctx: Context){
        val foundActivity : Activity = jsonToObject(ctx.body())
        if ((ActivityController.activityDao.update(activityId = ctx.pathParam("activity-id").toInt(), activity = foundActivity )) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }
}