package ie.setu.config

import ie.setu.controllers.HealthTrackerController
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
            path("/api/users") {
                get(HealthTrackerController::getAllUsers)
                post(HealthTrackerController::addUser)
                path("{user-id}") {
                    get(HealthTrackerController::getUserByUserId)
                    delete(HealthTrackerController::deleteUser)
                    patch(HealthTrackerController::updateUser)

                }
                path("/email/{email}") {
                    get(HealthTrackerController::getUserByEmail)
                }
            }
            path("/api/activities") {
                get(HealthTrackerController::getAllActivities)
                }
            path("/api/fooditems") {
                get(HealthTrackerController::getAllFoodItems)
            }
            path("/api/fitnessgoals") {
                get(HealthTrackerController::getAllFitnessGoals)
            }
            path("/api/meallogfooditems") {
                get(HealthTrackerController::getAllMealLogFoodItems)
            }
            path("/api/meallogs") {
                get(HealthTrackerController::getAllMealLogs)
            }
            path("/api/metrics") {
                get(HealthTrackerController::getAllMetrics)
            }
            path("/api/workoutplans") {
                get(HealthTrackerController::getAllWorkoutPlans)
            }

            }
        }
    }