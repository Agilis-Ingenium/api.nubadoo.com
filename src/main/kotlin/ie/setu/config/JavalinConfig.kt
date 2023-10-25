package ie.setu.config

import ie.setu.controllers.*

import ie.setu.utils.jsonObjectMapper
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.json.JavalinJackson

class JavalinConfig {

    val app = Javalin.create {

        //added this jsonMapper for our integration tests - serialise objects to json
        it.jsonMapper(JavalinJackson(jsonObjectMapper()))

        //added Vue capabilities
        it.staticFiles.enableWebjars()
        it.vue.vueAppName = "app" // only required for Vue 3, is defined in layout.html

    }.apply {
        exception(Exception::class.java) { e, _ -> e.printStackTrace() }
        error(404) { ctx -> ctx.json("404 : Not Found") }
    }

    fun startJavalinService(): Javalin {
        app.start(getRemoteAssignedPort())
        registerRoutes(app)
        return app
    }

    fun getJavalinService(): Javalin {
        registerRoutes(app)
        return app
    }

    private fun getRemoteAssignedPort(): Int {
        val remotePort = System.getenv("PORT")
        return if (remotePort != null) {
            Integer.parseInt(remotePort)
        } else 7060
    }

    private fun registerRoutes(app: Javalin) {
        app.routes {
            path("/v1") {
                path("users") {
                    get(UserController::getAllUsers)
                    post(UserController::addUser)
                    path("{user-id}") {
                        get(UserController::getUserByUserId)
                        delete(UserController::deleteUser)
                        patch(UserController::updateUser)
                    }
                    path("/email/{email}") {
                        get(UserController::getUserByEmail)
                    }
                }
                path("/activities") {
                    get(ActivityController::getAllActivities)
                }
                path("/food-items") {
                    get(FoodItemController::getAllFoodItems)
                }
                path("/fitness-goals") {
                    get(FitnessGoalController::getAllFitnessGoals)
                }
                /* path("/meallogfooditems") {
                    get(MealLogFoodItemController::getAllMealLogFoodItems)
                } */
                path("/meal-logs") {
                    get(MealLogController::getAllMealLogs)
                }
                path("/metrics") {
                    get(MetricController::getAllMetrics)
                }
                path("//workout-plans") {
                    get(WorkoutPlanController::getAllWorkoutPlans)
                }
                path("/ping") {
                    get(PongController::pong)
                }
            }
        }
    }
}
