package ie.setu.controllers

import io.javalin.http.Context
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

import ie.setu.domain.repository.FoodItemDAO
import ie.setu.domain.FoodItem
import ie.setu.domain.User

object FoodItemController {

    private val foodItemDao = FoodItemDAO()

    fun getAllFoodItems(ctx: Context) {
        ctx.json(foodItemDao.getAll())
    }

    fun getFoodItemByFoodItemName(ctx: Context) {
        val food = foodItemDao.findByName(ctx.pathParam("name"))
        if (food != null) {
            ctx.json(food)
        }
    }

    fun getFoodItemByFoodItemId(ctx: Context) {
        val food = foodItemDao.findById(ctx.pathParam("fooditem-id").toInt())
        if (food!= null) {
            ctx.json(food)
        }
    }

    fun addFoodItem(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val food = mapper.readValue<FoodItem>(ctx.body())
        foodItemDao.save(food)
        ctx.json(food)
    }

    fun deleteFoodItem(ctx: Context) {
        foodItemDao.delete(ctx.pathParam("food-item-id").toInt())
    }

    fun updateFoodItem(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val foodItemUpdates = mapper.readValue<FoodItem>(ctx.body())
        //FoodItemController.foodItemDao.update(
            //foodItemId = ctx.pathParam("foodItem-id").toInt(),
            //foodItems = foodItemUpdates
        //)
    }
}