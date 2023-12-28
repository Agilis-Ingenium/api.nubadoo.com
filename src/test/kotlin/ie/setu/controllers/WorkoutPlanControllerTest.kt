package ie.setu.controllers

import ie.setu.config.DbConfig
import ie.setu.helpers.ServerContainer
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WorkoutPlanControllerTest {

    private val db = DbConfig().getDbConnection()
    private val app = ServerContainer.instance
    private val origin = "http://localhost:" + app.port()

}