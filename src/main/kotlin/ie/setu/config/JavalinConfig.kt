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
import io.javalin.plugin.bundled.CorsContainer
import io.javalin.plugin.bundled.CorsPluginConfig

/**
 * Configuration class for setting up the Javalin web framework.
 * This class initializes Javalin and configures routes and settings.
 */
class JavalinConfig {

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


            it.plugins.enableCors { cors: CorsContainer ->
                    cors.add { it: CorsPluginConfig ->
                        it.anyHost()
                    }
                }
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
    fun getJavalinService(app: Javalin): Javalin {
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
                    post(ActivityController::addActivity)
                    path("{activity-id}") {
                        delete(ActivityController::deleteActivity)
                        patch(ActivityController::updateActivity)
                        get(ActivityController::getActivityByActivityId)
                    }
                    path("/user"){
                        path("{user-id}"){
                            get(ActivityController::getActivitiesByUserId)
                            delete(ActivityController::deleteActivitiesByUserId)
                        }
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
                path("/metrics") {
                    get(MetricController::getAllMetrics)
                    post(MetricController::addMetric)
                    path("{metric-id}") {
                        get(MetricController::getMetricByMetricId)
                        delete(MetricController::deleteMetric)
                        patch(MetricController::updateMetric)
                    }
                    path("/user") {
                        path("{user-id}") {
                            get(MetricController::getMetricsByUserId)
                            delete(MetricController::deleteMetricsByUserId)
                        }
                    }
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
                path("/meal-logs") {
                    get(MealLogController::getMealLogs)
                }
                path("/ping") {
                    get(PongController::pong)
                }
                //path("/dashboard") {
                //  path("{userId}") {
                //        get(ChartController::getChartDatabyUserId)
                //    }
                //}
            }
        }
    }
}
