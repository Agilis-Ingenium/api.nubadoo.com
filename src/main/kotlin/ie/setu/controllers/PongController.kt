package ie.setu.controllers

import io.javalin.http.Context
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object PongController {

    private val pong = object {
        val message = "Pong"
        val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
    }

    fun pong(ctx: Context) {
        ctx.json(pong)
    }
}

