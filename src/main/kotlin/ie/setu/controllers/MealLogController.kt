package ie.setu.controllers

import io.javalin.http.Context
import ie.setu.domain.repository.MealLogDAO

object MealLogController {

    private val mealLogsDao = MealLogDAO()

    fun getAllMealLogs(ctx: Context) {
        ctx.json(mealLogsDao.getAll())
    }
}