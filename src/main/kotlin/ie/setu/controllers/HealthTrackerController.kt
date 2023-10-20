package ie.setu.controllers

import io.javalin.http.Context
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

import ie.setu.domain.repository.UserDAO
import ie.setu.domain.User
import ie.setu.domain.repository.ActivityDAO
import ie.setu.domain.Activity
import ie.setu.domain.repository.FoodItemDAO
import ie.setu.domain.FoodItem
import ie.setu.domain.WorkoutPlan
import ie.setu.domain.repository.WorkoutPlanDAO
import ie.setu.domain.Metric
import ie.setu.domain.repository.MetricDAO
import ie.setu.domain.FitnessGoal
import ie.setu.domain.repository.FitnessGoalDAO
import ie.setu.domain.MealLog
import ie.setu.domain.repository.MealLogDAO
import ie.setu.domain.MealLogFoodItem
import ie.setu.domain.repository.MealLogFoodItemDAO

object HealthTrackerController {

    private val userDao = UserDAO()

    fun getAllUsers(ctx: Context) {
        ctx.json(userDao.getAll())
    }

    fun getUserByUserId(ctx: Context) {
        val user = userDao.findById(ctx.pathParam("user-id").toInt())
        if (user != null) {
            ctx.json(user)
        }
    }

    fun addUser(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val user = mapper.readValue<User>(ctx.body())
        userDao.save(user)
        ctx.json(user)
    }

    fun getUserByEmail(ctx: Context) {
        val user = userDao.findByEmail(ctx.pathParam("email"))
        if (user != null) {
            ctx.json(user)
        }
    }

    fun deleteUser(ctx: Context){
        userDao.delete(ctx.pathParam("user-id").toInt())
    }

    fun updateUser(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val userUpdates = mapper.readValue<User>(ctx.body())
        userDao.update(
            id = ctx.pathParam("user-id").toInt(),
            user = userUpdates
        )
    }

    private val activityDao = ActivityDAO()

    fun getAllActivities(ctx: Context) {
        ctx.json(activityDao.getAll())
    }

    private val foodItemDao = FoodItemDAO()

    fun getAllFoodItems(ctx: Context) {
        ctx.json(foodItemDao.getAll())
    }

    private val workoutPlanDao = WorkoutPlanDAO()

    fun getAllWorkoutPlans(ctx: Context) {
        ctx.json(workoutPlanDao.getAll())
    }

    private val metricsDao = MetricDAO()

    fun getAllMetrics(ctx: Context) {
        ctx.json(metricsDao.getAll())
    }

    private val fitnessGoalsDao = FitnessGoalDAO()

    fun getAllFitnessGoals(ctx: Context) {
        ctx.json(fitnessGoalsDao.getAll())
    }

    private val mealLogsDao = MealLogDAO()

    fun getAllMealLogs(ctx: Context) {
        ctx.json(mealLogsDao.getAll())
    }

    private val mealLogFoodItemsDao = MealLogFoodItemDAO()

    fun getAllMealLogFoodItems(ctx: Context) {
        ctx.json(mealLogFoodItemsDao.getAll())
    }
}