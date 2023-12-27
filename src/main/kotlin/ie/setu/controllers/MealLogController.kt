package ie.setu.controllers

import io.javalin.http.Context
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ie.setu.domain.MealLog
import io.javalin.openapi.*
import ie.setu.domain.repository.MealLogDAO

/**
 * Controller handling operations related to meal logs in the Health Tracker API.
 */
object MealLogController {

    private val mealLogDao = MealLogDAO()

    private val objectMapper = jacksonObjectMapper().apply {
        registerModule(JodaModule())
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    }

    /**
     * Retrieves all meal logs and sends them as a response in JSON format.
     *
     * @param ctx The context object representing the HTTP request and response context.
     */
    @OpenApi(
        summary = "Get all meal logs",
        operationId = "getAllMealLogs",
        tags = ["Meal Logs"],
        responses = [
            OpenApiResponse("200", [OpenApiContent(Array<MealLog>::class)])
        ],
        path = "/v1/meal-logs",
        methods = [HttpMethod.GET]
    )
    fun getMealLogs(ctx: Context) {
        val mealLog = mealLogDao.getAll()

        if (mealLog != null) {
            ctx.json(mealLog)
        } else {
            ctx.status(404).json("Meal log not found")
        }
    }
}
