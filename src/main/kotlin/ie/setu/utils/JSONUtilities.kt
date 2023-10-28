package ie.setu.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kong.unirest.HttpResponse
import kong.unirest.JsonNode

//More info: https://www.baeldung.com/jackson-object-mapper-tutorial
//           https://www.baeldung.com/jackson-serialize-dates
//           https://www.baeldung.com/kotlin/reified-functions

/**
 * Deserializes JSON data into a Kotlin object of the specified type.
 * @param json The JSON string to be deserialized.
 * @return An instance of type T representing the deserialized JSON data.
 * @reified T The type of object to deserialize the JSON into.
 */
inline fun <reified T: Any> jsonToObject(json: String) : T
        = jacksonObjectMapper()
    .registerModule(JodaModule())
    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    .readValue<T>(json)

/**
 * Deserializes JSON data from an HTTP response into a Kotlin object of the specified type.
 * @param jsonNode The HttpResponse object containing the JSON data.
 * @return An instance of type T representing the deserialized JSON data.
 * @reified T The type of object to deserialize the JSON into.
 */
inline fun <reified T: Any>  jsonNodeToObject(jsonNode : HttpResponse<JsonNode>) : T {
    return jsonToObject<T>(jsonNode.body.toString())
}

/**
 * Creates and configures an ObjectMapper for JSON serialization and deserialization.
 * @return An instance of ObjectMapper configured for JSON processing.
 */
fun jsonObjectMapper(): ObjectMapper
        = ObjectMapper()
    .registerModule(JavaTimeModule())
    .registerModule(JodaModule())
    .registerModule(KotlinModule.Builder().build())
    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

    // Prettify json output globally
    // https://stackoverflow.com/questions/17617370/pretty-printing-json-from-jackson-2-2s-objectmapper

    .enable(SerializationFeature.INDENT_OUTPUT)




