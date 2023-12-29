package ie.setu.controllers

import ie.setu.config.DbConfig
import ie.setu.helpers.ServerContainer
import org.junit.jupiter.api.TestInstance
import kong.unirest.Unirest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MealLogControllerTest {

    private val db = DbConfig().getDbConnection()
    private val app = ServerContainer.instance
    private val origin = "http://localhost:" + app.port() + "/v1"

    // IMPLEMENT

}