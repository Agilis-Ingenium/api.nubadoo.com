package ie.setu.controllers

import ie.setu.domain.Pong
import io.javalin.http.Context
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import io.javalin.openapi.*

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
    @OpenApi(
        summary = "Get Pong response",
        operationId = "pong",
        tags = ["Pong"],
        responses = [
            OpenApiResponse("200", [OpenApiContent(Pong::class)])
        ],
        path = "/v1/ping",
        methods = [HttpMethod.GET]
    )
    fun pong(ctx: Context) {
        ctx.json(pong)
    }
}

