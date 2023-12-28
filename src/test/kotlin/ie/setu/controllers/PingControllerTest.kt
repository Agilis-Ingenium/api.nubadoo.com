package ie.setu.controllers

import ie.setu.config.DbConfig
import ie.setu.helpers.ServerContainer
import org.junit.jupiter.api.TestInstance
import kong.unirest.Unirest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PingControllerTest {

    private val db = DbConfig().getDbConnection()
    private val app = ServerContainer.instance
    private val origin = "http://localhost:" + app.port() + "/v1"

    @Test
    fun `get pong health message back from api returns 200 or error (4xx or 5xx) response`() {
        val response = Unirest.get(origin + "/ping").asString()
        assertEquals(200, response.status)

        println("Success : " + response.isSuccess)
        println("Status code : " + response.status)
        println("Body : " + response.body)

    }

}