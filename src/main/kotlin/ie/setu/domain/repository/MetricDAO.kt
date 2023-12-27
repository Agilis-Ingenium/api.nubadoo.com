package ie.setu.domain.repository

import ie.setu.domain.Activity
import ie.setu.domain.FoodItem
import ie.setu.domain.Metric
import ie.setu.domain.db.Activities
import ie.setu.domain.db.FoodItems
import ie.setu.domain.db.Metrics
import ie.setu.utils.mapToActivity
import ie.setu.utils.mapToFoodItem
import org.jetbrains.exposed.sql.transactions.transaction
import ie.setu.utils.mapToMetric
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

/**
 * Data Access Object (DAO) for managing users' metric data in the Health Tracker app.
 *
 * This class provides methods to retrieve, create, update, and delete metrics in the database.
 *
 * @author Warren Byron (adapted from SETU Msc Computing Agile Dev course content).
 */
class MetricDAO {

    /**
     * Retrieves a list of all metrics from the database.
     *
     * @return An ArrayList of [Metric] objects representing all metrics.
     */
    fun getAll(): ArrayList<Metric> {
        val metricList: ArrayList<Metric> = arrayListOf()
        transaction {
            Metrics.selectAll().map {
                metricList.add(mapToMetric(it)) }
        }
        return metricList
    }

    /**
     * Saves a new metric to the database.
     *
     * @param metric The [Metric] object to be saved.
     * @return The unique identifier (metricId) of the newly saved metric.
     */
    fun save(metric: Metric) : Int {
        return transaction {
            Metrics.insert {
                it[userId] = metric.userId
                it[weight] = metric.weight
                it[height] = metric.height
                it[bmi] = metric.bmi
                it[systolicBloodPressure] = metric.systolicBloodPressure
                it[diastolicBloodPressure] = metric.diastolicBloodPressure
                it[heartRate] = metric.heartRate
                it[bloodSugar] = metric.bloodSugar
                it[sleepDuration] = metric.sleepDuration
                it[sleepQuality] = metric.sleepQuality
                it[createdAt] = metric.createdAt
            } get Metrics.metricId
        }
    }

    /**
     * Retrieves a metric by its unique identifier.
     *
     * @param id The unique identifier of the metric to retrieve.
     * @return A [Metric] object representing the metric with the specified ID, or null if not found.
     */
    fun findById(id: Int) : Metric? {
        return transaction {
            Metrics.select() {
                Metrics.metricId eq id}
                .map{ mapToMetric(it) }
                .firstOrNull()
        }
    }

    /**
     * Deletes a metric from the database by its unique identifier.
     *
     * @param metricId The unique identifier of the metric to delete.
     * @return The number of rows affected by the deletion operation.
     */
    fun delete(metricId: Int) : Int {
        return transaction{
            Metrics.deleteWhere{
                Metrics.metricId eq metricId
            }
        }
    }

    /**
     * Find all metrics for a specific user ID.
     * @param userId The user ID for which to find metrics.
     * @return A list of metrics associated with the given user ID.
     */
    fun findByUserId(userId: Int): List<Metric> {
        return transaction {
            Metrics
                .select { Metrics.userId eq userId }
                .map { mapToMetric(it) }
        }
    }

    /**
     * Updates an existing metric in the database.
     *
     * @param metricId The unique identifier of the metric to update.
     * @param metric The updated [Metric] object.
     * @return The number of rows affected by the update operation.
     */
    fun update(metricId: Int, metric: Metric) : Int {
        return transaction {
            Metrics.update ({
                Metrics.metricId eq metricId}) {
                it[userId] = metric.userId
                it[weight] = metric.weight
                it[height] = metric.height
                it[bmi] = metric.bmi
                it[systolicBloodPressure] = metric.systolicBloodPressure
                it[diastolicBloodPressure] = metric.diastolicBloodPressure
                it[heartRate] = metric.heartRate
                it[bloodSugar] = metric.bloodSugar
                it[sleepDuration] = metric.sleepDuration
                it[sleepQuality] = metric.sleepQuality
                it[createdAt] = metric.createdAt
            }
        }
    }
}


