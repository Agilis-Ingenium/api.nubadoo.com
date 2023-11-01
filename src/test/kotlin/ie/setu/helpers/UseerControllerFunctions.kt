package ie.setu.helpers

import ie.setu.config.DbConfig
import kong.unirest.HttpResponse
import kong.unirest.JsonNode
import kong.unirest.Unirest
import org.joda.time.DateTime

private val db = DbConfig().getDbConnection()
private val app = ServerContainer.instance
private val origin = "http://localhost:" + app.port()

//helper function to add a test user to the database
fun addUser (name: String, email: String): HttpResponse<JsonNode> {

    // NOTE: Hardcoding some values that are non-nullable on this extended user model
    return Unirest.post(origin + "/v1/users").body("{\"username\":\"$name\", \n    \"email\":\"$email\", \n    \"password\":\"123\", \n    \"firstName\":\"A\",\n    \"lastName\": \"B\",\n    \"gender\":\"male\",\n   \"registrationDate\":\"${DateTime.now()}\"}").asJson()
}

//helper function to delete a test user from the database
fun deleteUser (id: Int): HttpResponse<String> {
    return Unirest.delete(origin + "/v1/users/$id").asString()
}

//helper function to retrieve a test user from the database by email
fun retrieveUserByEmail(email : String) : HttpResponse<String> {
    return Unirest.get(origin + "/v1/users/email/${email}").asString()
}

//helper function to retrieve a test user from the database by id
fun retrieveUserById(id: Int) : HttpResponse<String> {
    return Unirest.get(origin + "/v1/users/${id}").asString()
}

//helper function to add a test user to the database
fun updateUser (id: Int, name: String, email: String): HttpResponse<JsonNode> {

    return Unirest.patch(origin + "/v1/users/$id")

        // Hard coding in values that cannot be nullable because the properties of the uses
        // extend the one from the labs - need to address elegantly

        .body("{\"username\":\"$name\", \n    \"email\":\"$email\", \n    \"password\":\"123\", \n    \"firstName\":\"A\",\n    \"lastName\": \"B\",\n    \"gender\":\"male\",\n   \"registrationDate\":\"${DateTime.now()}\"}")
        .asJson()
}