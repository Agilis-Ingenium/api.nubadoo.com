package ie.setu.controllers

import io.javalin.http.Context
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

import ie.setu.domain.repository.FoodItemDAO
import ie.setu.domain.FoodItem
import ie.setu.domain.User
import ie.setu.utils.jsonToObject

object FoodItemController {

    private val foodItemDao = FoodItemDAO()

    fun getAllFoodItems(ctx: Context) {
        val foodItems = foodItemDao.getAll()
        if (foodItems.size != 0) {
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
        ctx.json(foodItems)
    }

    fun getFoodItemByFoodItemId(ctx: Context) {
        val foodItem = foodItemDao.findById(ctx.pathParam("food-item-id").toInt())
        if (foodItem != null) {
            ctx.json(foodItem)
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
    }

    fun addFoodItem(ctx: Context) {
        val foodItem : FoodItem = jsonToObject(ctx.body())
        val foodItemId = foodItemDao.save(foodItem)
        if (foodItemId != null) {
            foodItem.foodItemId = foodItemId
            ctx.json(foodItem)
            ctx.status(201)
        }
    }


    fun deleteFoodItem(ctx: Context) {
        if (foodItemDao.delete(ctx.pathParam("food-item-id").toInt()) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }

    fun updateFoodItem(ctx: Context) {
        val foundFoodItem : FoodItem = jsonToObject(ctx.body())
        if ((FoodItemController.foodItemDao.update(foodItemId = ctx.pathParam("food-item-id").toInt(), foodItem=foundFoodItem)) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }
}