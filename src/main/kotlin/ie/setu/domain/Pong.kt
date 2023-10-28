package ie.setu.domain

/**
 * Represents a response message used in the "Ping" endpoint of the Health Tracker app.
 *
 * The "Ping" endpoint responds with a "Pong" message when called with a GET method.
 *
 * @property message The response message, which is typically "Pong."
 *
 * @author Warren Byron.
 */
data class Pong (
    var message:String
)