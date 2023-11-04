package ie.setu.helpers

import ie.setu.config.DbConfig
import kong.unirest.HttpResponse
import kong.unirest.JsonNode
import kong.unirest.Unirest
import org.joda.time.DateTime

private val db = DbConfig().getDbConnection()
private val app = ServerContainer.instance
private val origin = "http://localhost:" + app.port()


//helper function to add an activity
        fun addActivity(activityId: Int,
                        activityDate: DateTime,
                        workoutIntensity: String,
                        distanceKm: Double?,
                        durationMinutes: Int,
                        activityType: String,
                        userId: Int ): HttpResponse<JsonNode> {
    return Unirest.post(origin + "/v1/activities")
        .body("""
                {   
                   "activityId":$activityId,
                   "activityDate":"$activityDate",
                   "workoutIntensity":"$workoutIntensity",
                   "distanceKm":$distanceKm,
                   "durationMinutes": $durationMinutes,
                   "activityType":"$activityType",
                   "userId":$userId
                }
            """.trimIndent())
        .asJson()
}