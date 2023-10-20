package ie.setu.domain.repository

import ie.setu.domain.Metric
import ie.setu.domain.db.Metrics
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ie.setu.utils.mapToMetric
import org.jetbrains.exposed.sql.select
class MetricDAO {

    fun getAll(): ArrayList<Metric> {
        val activityList: ArrayList<Metric> = arrayListOf()
        transaction {
            Metrics.selectAll().map {
                activityList.add(mapToMetric(it)) }
        }
        return activityList
    }
}