package ie.setu.helpers

import ie.setu.config.DbConfig
import kong.unirest.HttpResponse
import kong.unirest.JsonNode
import kong.unirest.Unirest

private val db = DbConfig().getDbConnection()
private val app = ServerContainer.instance
private val origin = "http://localhost:" + app.port()

//helper function to add a test user to the database
private fun addUser (name: String, email: String): HttpResponse<JsonNode> {
    return Unirest.post(origin + "/api/users")
        .body("{\"name\":\"$name\", \"email\":\"$email\"}")
        .asJson()
}

//helper function to delete a test user from the database
private fun deleteUser (id: Int): HttpResponse<String> {
    return Unirest.delete(origin + "/api/users/$id").asString()
}

//helper function to retrieve a test user from the database by email
private fun retrieveUserByEmail(email : String) : HttpResponse<String> {
    return Unirest.get(origin + "/api/users/email/${email}").asString()
}

//helper function to retrieve a test user from the database by id
private fun retrieveUserById(id: Int) : HttpResponse<String> {
    return Unirest.get(origin + "/api/users/${id}").asString()
}

//helper function to add a test user to the database
private fun updateUser (id: Int, name: String, email: String): HttpResponse<JsonNode> {
    return Unirest.patch(origin + "/api/users/$id")
        .body("{\"name\":\"$name\", \"email\":\"$email\"}")
        .asJson()
}