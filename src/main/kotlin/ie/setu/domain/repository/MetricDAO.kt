package ie.setu.domain.repository

import ie.setu.domain.Metric
import ie.setu.domain.WorkoutPlan
import ie.setu.domain.db.Metrics
import ie.setu.domain.db.WorkoutPlans
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ie.setu.utils.mapToMetric
import org.jetbrains.exposed.sql.insert

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
        val activityList: ArrayList<Metric> = arrayListOf()
        transaction {
            Metrics.selectAll().map {
                activityList.add(mapToMetric(it)) }
        }
        return activityList
    }

    /**
     * Saves a new workout plan to the database.
     *
     * @param workoutPlan The [WorkoutPlan] object to be saved.
     * @return The unique identifier (planId) of the newly saved workout plan.
     */
    fun save(metric: Metric) : Int {
        return transaction {
            Metrics.insert {
                it[metricId] = metric.metricId
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
}


