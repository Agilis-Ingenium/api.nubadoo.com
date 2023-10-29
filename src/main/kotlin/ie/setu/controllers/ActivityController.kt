package ie.setu.controllers

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.javalin.http.Context
import ie.setu.domain.Activity
import ie.setu.domain.repository.ActivityDAO
import ie.setu.domain.repository.UserDAO
import ie.setu.utils.jsonToObject

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
    fun addActivity(ctx: Context) {
        val activity : Activity = jsonToObject(ctx.body())
        val activityId = activityDao.save(activity)
        if (activityId != null) {
            activity.activityId = activityId
            ctx.json(activity)
            ctx.status(201)
        }
    }
}