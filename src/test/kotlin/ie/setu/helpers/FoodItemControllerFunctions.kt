package ie.setu.helpers

import ie.setu.config.DbConfig
import kong.unirest.HttpResponse
import kong.unirest.JsonNode
import kong.unirest.Unirest
import org.joda.time.DateTime

private val db = DbConfig().getDbConnection()
private val app = ServerContainer.instance
private val origin = "http://localhost:" + app.port()

//helper function to add a test food item to the database
fun addFoodItem (name: String): HttpResponse<JsonNode>? {

    // NOTE: Hardcoding some values that are non-nullable on this extended food item model
    return Unirest.post(origin + "/v1/food-items").body("{\"name\": \"$name\",\"calories\": 55,\"carbohydrates\": 11.2,\"proteins\": 3.7,\"fats\": 0.6,\"vitamins\": \"Vitamin C, Vitamin K\",\"minerals\": \"Folate, Potassium\"}").asJson()
}

//helper function to delete a test food item from the database
fun deleteFoodItem (id: Int): HttpResponse<String> {
    return Unirest.delete(origin + "/v1/food-items/$id").asString()
}

//helper function to retrieve a test food item from the database by id
fun retrieveFoodItemById(id: Int) : HttpResponse<String> {
    return Unirest.get(origin + "/v1/food-items/${id}").asString()
}

//helper function to update a test food item to the database
fun updateFoodItem (id: Int, name: String, email: String): HttpResponse<JsonNode> {

    return Unirest.patch(origin + "/v1/food-items/$id")

        // Hard coding in values that cannot be nullable because the properties of the uses
        // extend the one from the labs - need to address elegantly

        .body("{\"name\": \"$name\",\"calories\": 55,\"carbohydrates\": 11.2,\"proteins\": 3.7,\"fats\": 0.6,\"vitamins\": \"Vitamin C, Vitamin K\",\"minerals\": \"Folate, Potassium\"}")
        .asJson()
}