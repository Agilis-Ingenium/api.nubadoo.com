package ie.setu.helpers

import ie.setu.config.DbConfig
import kong.unirest.HttpResponse
import kong.unirest.JsonNode
import kong.unirest.Unirest
import org.joda.time.DateTime

private val db = DbConfig().getDbConnection()
private val app = ServerContainer.instance
private val origin = "http://localhost:" + app.port()


//helper function to add a fitness goal
        fun addFitnessGoal(
                        goalId: Int,
                        userId: Int,
                        goalType: String,
                        targetValue: Double,
                        targetDate: DateTime?,
                        achieved: Boolean
        ): HttpResponse<JsonNode> {
    return Unirest.post(origin + "/v1/fitness-goals")
        .body("""
                {   
                   "goalId":$goalId,
                   "userId":"$userId",
                   "goalType":"$goalType",
                   "targetValue":$targetValue,
                   "targetDate": $targetDate,
                   "achieved":"$achieved",
                }
            """.trimIndent())
        .asJson()
}