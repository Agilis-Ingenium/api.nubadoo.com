package ie.setu.controllers

import io.javalin.http.Context
import ie.setu.domain.repository.ActivityDAO

object ActivityController {

    private val activityDao = ActivityDAO()

    fun getAllActivities(ctx: Context) {
        ctx.json(activityDao.getAll())
    }

}