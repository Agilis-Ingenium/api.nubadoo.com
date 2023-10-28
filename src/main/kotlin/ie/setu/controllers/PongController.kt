package ie.setu.controllers

import io.javalin.http.Context
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Controller for handling the "Pong" response.
 */
object PongController {

    private val pong = object {
        val message = "Pong"
        val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
    }

    /**
     * Responds with "Pong" and the current timestamp.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    fun pong(ctx: Context) {
        ctx.json(pong)
    }
}

