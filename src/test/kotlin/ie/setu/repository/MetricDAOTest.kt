package ie.setu.repository

import ie.setu.domain.Metric
import ie.setu.domain.db.Metrics
import ie.setu.domain.db.Users
import ie.setu.domain.repository.MetricDAO
import ie.setu.domain.repository.UserDAO
import ie.setu.helpers.metrics
import org.junit.jupiter.api.Assertions.assertEquals
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.joda.time.DateTime

val metric1 = metrics.get(0)
val metric2 = metrics.get(1)
val metric3 = metrics.get(2)

class MetricDAOTest {

    companion object {

        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    @Nested
    inner class ReadMetrics {
        @Test
        fun `getting all metrics from a populated table returns all rows`() {
            transaction {

                val metricDAO = populateMetricTable()

                println(metricDAO.getAll())

                assertEquals(3, metricDAO.getAll().size)
            }
        }

        @Test
        fun `get metric by id that doesn't exist, results in no metric returned`() {
            transaction {

                val metricDAO = populateMetricTable()

                println(metricDAO.findById(4))

                assertEquals(null, metricDAO.findById(4))
            }
        }

        @Test
        fun `get metric plan by id that exists, results in a correct metric returned`() {
            transaction {

                val metricDAO = populateMetricTable()

                println(metric3)

                assertEquals(metric3, metricDAO.findById(3))
            }
        }

        @Test
        fun `get all metrics over empty table returns none`() {
            transaction {

                SchemaUtils.create(Metrics)
                val metricDAO = MetricDAO()

                println(metricDAO.getAll())

                assertEquals(0, metricDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class CreateMetric {
        @Test
        fun `multiple metrics added to table can be retrieved successfully`() {
            transaction {

                val metricDAO = populateMetricTable()

                println(metricDAO.getAll())

                assertEquals(3, metricDAO.getAll().size)
                assertEquals(metric1, metricDAO.findById(metric1.metricId))
                assertEquals(metric2, metricDAO.findById(metric2.metricId))
                assertEquals(metric3, metricDAO.findById(metric3.metricId))
            }
        }
    }


    @Nested
    inner class DeleteMetrics {
        @Test
        fun `deleting a non-existant metric in table results in no deletion`() {
            transaction {

                val metricDAO = populateMetricTable()

                println(metricDAO.getAll())

                assertEquals(3, metricDAO.getAll().size)
                metricDAO.delete(4)
                assertEquals(3, metricDAO.getAll().size)
            }
        }

        @Test
        fun `deleting an existing metric in table results in record being deleted`() {
            transaction {

                val metricDAO = populateMetricTable()

                println(metricDAO.getAll())

                assertEquals(3, metricDAO.getAll().size)
                metricDAO.delete(metric3.metricId)
                assertEquals(2, metricDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class UpdateMetrics {

        @Test
        fun `updating existing metrics in table results in successful update`() {
            transaction {

                val metricDAO = populateMetricTable()

                val metric3Updated = Metric(
                    metricId = 3,
                    userId = 1,
                    weight = 180.0,
                    height = 183.0,
                    bmi = 12.0,
                    systolicBloodPressure = 160,
                    diastolicBloodPressure = 170,
                    heartRate = 62,
                    bloodSugar = 12.0,
                    sleepDuration = 8,
                    sleepQuality = "good",
                    createdAt = DateTime.parse("2023-10-24")
                )

                metricDAO.update(metric3Updated.metricId, metric3Updated)

                val toCompare = metricDAO.findById(3)

                println(toCompare)
                println("---")
                println(metric3Updated)

                assertEquals(metric3Updated, toCompare)
            }
        }

        @Test
        fun `updating non-existant metric in table results in no updates`() {
            transaction {

                val metricDAO = populateMetricTable()

                val metric4Updated = Metric(
                    metricId = 4,
                    userId = 1,
                    weight = 180.0,
                    height = 183.0,
                    bmi = 12.0,
                    systolicBloodPressure = 160,
                    diastolicBloodPressure = 170,
                    heartRate = 62,
                    bloodSugar = 12.0,
                    sleepDuration = 8,
                    sleepQuality = "good",
                    createdAt = DateTime.parse("2023-10-24"))

                println(metricDAO.findById(4))
                println("---")
                println(metricDAO.getAll().size)

                metricDAO.update(4, metric4Updated)
                assertEquals(null, metricDAO.findById(4))
                assertEquals(3, metricDAO.getAll().size)
            }
        }
    }

    internal fun populateMetricTable(): MetricDAO {

        SchemaUtils.create(Users)
        val userDAO = UserDAO()
        userDAO.save(userdata1)
        userDAO.save(userdata2)
        userDAO.save(userdata3)
        userDAO.save(userdata4)

        SchemaUtils.create(Metrics)
        val metricDAO = MetricDAO()
        metricDAO.save(metric1)
        metricDAO.save(metric2)
        metricDAO.save(metric3)
        return metricDAO
    }
}