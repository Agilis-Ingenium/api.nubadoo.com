package ie.setu.controllers

import io.javalin.http.Context

import ie.setu.domain.repository.FoodItemDAO
import ie.setu.domain.FoodItem

object FoodItemController {

    private val foodItemDao = FoodItemDAO()

    fun getAllFoodItems(ctx: Context) {
        ctx.json(foodItemDao.getAll())
    }

    fun getFoodByFoodItemName(ctx: Context) {
        val food = FoodItemController.foodItemDao.findByName(ctx.pathParam("name").toString())
        if (food != null) {
            ctx.json(food)
        }
    }

}