package ie.setu.controllers

import ie.setu.domain.repository.MetricDAO
import io.javalin.http.Context

object MetricController {

    private val metricDao = MetricDAO()

    fun getAllMetrics(ctx: Context) {
        ctx.json(metricDao.getAll())
    }
}