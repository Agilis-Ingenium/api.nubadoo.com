package ie.setu

import ie.setu.config.DbConfig
import ie.setu.config.JavalinConfig

/**
 * The entry point of the Health Tracker application.
 *
 * This function initializes the application by configuring the database connection
 * and starting the Javalin web service.
 */
fun main() {

    DbConfig().getDbConnection()
    JavalinConfig().startJavalinService()

}