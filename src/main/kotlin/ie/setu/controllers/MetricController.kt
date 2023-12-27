package ie.setu.controllers

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.ser.FilterProvider
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ie.setu.domain.Metric
import ie.setu.domain.repository.MetricDAO
import ie.setu.domain.repository.UserDAO
import ie.setu.utils.jsonToObject
import io.javalin.http.Context
import io.javalin.openapi.*

/**
 * Controller for managing users' metrics.
 */
object MetricController {

    private val metricDao = MetricDAO()
    private val userDao = UserDAO()

    /**
     * Get a list of all metrics.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Get all metrics",
        operationId = "getAllMetrics",
        tags = ["Metrics"],
        responses = [OpenApiResponse("200", [OpenApiContent(Array<Metric>::class)])],
        path = "/v1/metrics",
        methods = [HttpMethod.GET]
    )
    fun getAllMetrics(ctx: Context) {
        val metrics = metricDao.getAll()
        if (metrics.size != 0) {
            ctx.status(200)
        } else {
            ctx.status(404)
        }
        ctx.json(metrics)
    }

    /**
     * Get a specific metric by its ID.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Get a specific metric by ID",
        operationId = "getMetricByMetricId",
        tags = ["Metric"],
        responses = [
            OpenApiResponse("200", [OpenApiContent(Metric::class)]),
            OpenApiResponse("404", [OpenApiContent(String::class)])
        ],
        path = "/v1/metrics/{metric-id}",
        methods = [HttpMethod.GET]
    )
    fun getMetricByMetricId(ctx: Context) {
        val metric = metricDao.findById(ctx.pathParam("metric-id").toInt())
        if (metric != null) {
            ctx.json(metric)
            ctx.status(200)
        } else {
            ctx.status(404)
        }
    }

    /**
     * Add a new metric.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Add a new metric",
        operationId = "addMetric",
        tags = ["Metric"],
        responses = [
            OpenApiResponse("201", [OpenApiContent(Metric::class)])
        ],
        path = "/v1/metrics",
        methods = [HttpMethod.POST],
        requestBody = OpenApiRequestBody([OpenApiContent(Metric::class)]),
    )
    fun addMetric(ctx: Context) {
        val metric: Metric = jsonToObject(ctx.body())
        val metricId = metricDao.save(metric)
        if (metricId != null) {
            metric.metricId = metricId
            ctx.json(metric)
            ctx.status(201)
        }
    }

    /**
     * Delete a metric by its ID.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Delete a metric by ID",
        operationId = "deleteMetric",
        tags = ["Metric"],
        responses = [
            OpenApiResponse("204", [OpenApiContent(Metric::class)]),
            OpenApiResponse("404", [OpenApiContent(String::class)])
        ],
        path = "/v1/metrics/{metric-id}",
        methods = [HttpMethod.DELETE]
    )
    fun deleteMetric(ctx: Context) {
        if (metricDao.delete(ctx.pathParam("metric-id").toInt()) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }

    /**
     * Update an existing metric.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Update an existing metric",
        operationId = "updateMetric",
        tags = ["Metric"],
        responses = [
            OpenApiResponse("204", [OpenApiContent(Metric::class)]),
            OpenApiResponse("404", [OpenApiContent(String::class)])
        ],
        path = "/v1/metrics/{metric-id}",
        methods = [HttpMethod.PATCH],
        requestBody = OpenApiRequestBody([OpenApiContent(Metric::class)])
    )
    fun updateMetric(ctx: Context) {
        val foundMetric: Metric = jsonToObject(ctx.body())
        if ((MetricController.metricDao.update(
                metricId = ctx.pathParam("metric-id").toInt(),
                metric = foundMetric
            )) != 0
        )
            ctx.status(204)
        else
            ctx.status(404)
    }


    /**
     * Retrieves metrics based on the user ID and filters if specified.
     *
     * @param ctx The context object to handle the HTTP request and response.
     * @return JSON data of metrics based on the specified filter or all metrics if no filter is applied.
     */
    @OpenApi(
        summary = "Get metrics based on the user ID and optional filters",
        operationId = "getMetricsByUserId",
        tags = ["Metrics"],
        responses = [
            OpenApiResponse("200", [OpenApiContent(Metric::class)]),
            OpenApiResponse("404", [OpenApiContent(String::class)])
        ],
        path = "/v1/metrics/user/{user-id}",
        methods = [HttpMethod.GET]
    )
    fun getMetricsByUserId(ctx: Context) {
        val userId = ctx.pathParam("user-id").toIntOrNull()
        if (userId != null) {
            val user = userDao.findById(userId)
            if (user != null) {
                val metrics = MetricController.metricDao.findByUserId(userId)
                if (metrics.isNotEmpty()) {

                    // Query Param Filter Evaluation
                    val filteredMetrics = when (ctx.queryParam("filter")) {
                        "health" -> metrics.map { mapOf(
                            "userId" to it.userId,
                            "createdAt" to it.createdAt,
                            "heartRate" to it.heartRate,
                            "systolicBloodPressure" to it.systolicBloodPressure,
                            "diastolicBloodPressure" to it.diastolicBloodPressure,
                            "bloodSugar" to it.bloodSugar
                        ) }
                        "physique" -> metrics.map { mapOf(
                            "userId" to it.userId,
                            "createdAt" to it.createdAt,
                            "bmi" to it.bmi,
                            "weight" to it.weight,
                            "height" to it.height
                        ) }
                        "sleep" -> metrics.map { mapOf(
                            "userId" to it.userId,
                            "createdAt" to it.createdAt,
                            "sleepDuration" to it.sleepDuration,
                            "sleepQuality" to it.sleepQuality
                        ) }
                        else -> metrics.map { // Default case
                            mapOf(
                                "userId" to it.userId,
                                "createdAt" to it.createdAt,
                                "bmi" to it.bmi,
                                "weight" to it.weight,
                                "height" to it.height,
                                "heartRate" to it.heartRate,
                                "systolicBloodPressure" to it.systolicBloodPressure,
                                "diastolicBloodPressure" to it.diastolicBloodPressure,
                                "bloodSugar" to it.bloodSugar,
                                "sleepDuration" to it.sleepDuration,
                                "sleepQuality" to it.sleepQuality
                            )
                        }
                    }

                    // Prep output
                    val mapper = jacksonObjectMapper().apply {
                        registerModule(JodaModule())
                        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    }

                    val jsonString = mapper.writeValueAsString(filteredMetrics)
                    ctx.json(jsonString)
                    return // Exit function after sending the response
                }
            }
        }
        ctx.status(404) // User or metrics not found
    }

    /**
     * Deletes metrics by user ID.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Delete metrics by user ID",
        operationId = "deleteMetricsByUserId",
        tags = ["Metric"],
        responses = [
            OpenApiResponse("204", [OpenApiContent(Array<Metric>::class)]),
            OpenApiResponse("404", [OpenApiContent(String::class)])
        ],
        path = "/v1/metrics/user/{user-id}",
        methods = [HttpMethod.DELETE]
    )
    fun deleteMetricsByUserId(ctx: Context) {
        if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val metrics = metricDao.findByUserId(ctx.pathParam("user-id").toInt())
            if (metrics.isNotEmpty()) {
                metrics.forEach {
                    MetricController.metricDao.delete(it.metricId)
                }
            } else
                ctx.status(404)
        }
    }
}