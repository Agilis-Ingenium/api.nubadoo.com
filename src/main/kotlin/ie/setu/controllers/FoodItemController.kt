package ie.setu.controllers

import io.javalin.http.Context
import ie.setu.domain.repository.FoodItemDAO

object FoodItemController {

    private val foodItemDao = FoodItemDAO()

    fun getAllFoodItems(ctx: Context) {
        ctx.json(foodItemDao.getAll())
    }

}