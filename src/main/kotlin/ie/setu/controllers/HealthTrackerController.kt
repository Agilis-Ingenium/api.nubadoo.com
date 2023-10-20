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

}