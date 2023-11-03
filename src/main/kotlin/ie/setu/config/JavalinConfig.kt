package ie.setu.config

import ie.setu.controllers.*

import ie.setu.utils.jsonObjectMapper
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.json.JavalinJackson
import io.javalin.openapi.plugin.OpenApiConfiguration
import io.javalin.openapi.plugin.OpenApiPlugin
import io.javalin.openapi.plugin.redoc.ReDocConfiguration
import io.javalin.openapi.plugin.redoc.ReDocPlugin
import io.javalin.openapi.plugin.swagger.SwaggerConfiguration
import io.javalin.openapi.plugin.swagger.SwaggerPlugin

/**
 * Configuration class for setting up the Javalin web framework.
 * This class initializes Javalin and configures routes and settings.
 */
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

    /**
     * Starts the Javalin web service, registers routes, and returns the Javalin instance.
     *
     * @return The Javalin instance representing the started web service.
     */
    fun startJavalinService(): Javalin {
        val app = Javalin.create {
            //add this jsonMapper to serialise objects to json
            it.jsonMapper(JavalinJackson(jsonObjectMapper()))
            it.plugins.register(SwaggerPlugin(SwaggerConfiguration()))
            it.plugins.register(ReDocPlugin(ReDocConfiguration()))
            it.plugins.register(OpenApiPlugin(OpenApiConfiguration().apply {
                info.title = "Javalin OpenAPI example"
            }))
            }
            .apply{
                exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
                error(404) { ctx -> ctx.json("404 - Not Found") }
            }
            .start(getRemoteAssignedPort())

        registerRoutes(app)
        return app
    }

    /**
     * Gets the Javalin service instance.
     *
     * @return The Javalin instance representing the web service.
     */
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
                        path("activities"){
                            get(ActivityController::getActivitiesByUserId)
                            delete(ActivityController::deleteActivitiesByUserId)
                        }
                    }
                    path("/email/{email}") {
                        get(UserController::getUserByEmail)
                    }
                }
                path("/activities") {
                    get(ActivityController::getAllActivities)
                    post(ActivityController::addActivity)
                    path("{activity-id}") {
                        delete(ActivityController::deleteActivity)
                        patch(ActivityController::updateActivity)
                        get(ActivityController::getActivityByActivityId)
                    }
                }
                path("food-items") {
                    get(FoodItemController::getAllFoodItems)
                    post(FoodItemController::addFoodItem)
                    path("{food-item-id}") {
                        get(FoodItemController::getFoodItemByFoodItemId)
                        delete(FoodItemController::deleteFoodItem)
                        patch(FoodItemController::updateFoodItem)
                    }
                }
                path("/fitness-goals") {
                    get(FitnessGoalController::getAllFitnessGoals)
                    post(FitnessGoalController::addFitnessGoal)
                    path("{fitness-goal-id}") {
                        get(FitnessGoalController::getFitnessGoalByFitnessGoalId)
                        delete(FitnessGoalController::deleteFitnessGoal)
                        patch(FitnessGoalController::updateFitnessGoal)
                    }
                }
                /* REMOVED FOR NOW - NOT IMPLEMENTED YET

                path("/meallogfooditems") {
                    get(MealLogFoodItemController::getAllMealLogFoodItems)
                } */
                path("/meal-logs") {
                    get(MealLogController::getAllMealLogs)
                }
                path("/metrics") {
                    get(MetricController::getAllMetrics)
                }
                path("/workout-plans") {
                    get(WorkoutPlanController::getAllWorkoutPlans)
                    post(WorkoutPlanController::addWorkoutPlan)
                    path("{workout-plan-id}") {
                        get(WorkoutPlanController::getWorkoutPlanByWorkoutPlanId)
                        delete(WorkoutPlanController::deleteWorkoutPlan)
                        patch(WorkoutPlanController::updateWorkoutPlan)
                    }
                }
                path("/ping") {
                    get(PongController::pong)
                }
            }
        }
    }
}