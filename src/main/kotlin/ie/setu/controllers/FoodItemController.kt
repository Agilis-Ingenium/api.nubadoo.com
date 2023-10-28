package ie.setu.controllers

import ie.setu.domain.FitnessGoal
import io.javalin.http.Context
import ie.setu.domain.repository.FoodItemDAO
import ie.setu.domain.FoodItem
import io.javalin.openapi.*
import ie.setu.utils.jsonToObject

/**
 * Controller for managing food items.
 */
object FoodItemController {

    private val foodItemDao = FoodItemDAO()

    /**
     * Get a list of all food items.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Get all food items",
        operationId = "getAllFoodItems",
        tags = ["Food"],
        responses = [OpenApiResponse("200", [OpenApiContent(Array<FoodItem>::class)])],
        path = "/v1/food-items",
        methods = [HttpMethod.GET]
    )
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

    /**
     * Get a specific food item by its ID.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Get a specific food item by ID",
        operationId = "getFoodItemByFoodItemId",
        tags = ["Food"],
        responses = [
            OpenApiResponse("200", [OpenApiContent(FitnessGoal::class)]),
            OpenApiResponse("404", [OpenApiContent(String::class)])
        ],
        path = "/v1/food-items/{food-item-id}",
        methods = [HttpMethod.GET]
    )
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

    /**
     * Add a new food item.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Add a new food item",
        operationId = "addFoodItem",
        tags = ["Food"],
        responses = [
            OpenApiResponse("201", [OpenApiContent(FoodItem::class)])
                    ],
        path = "/v1/food-items",
        methods = [HttpMethod.POST],
        requestBody = OpenApiRequestBody([OpenApiContent(FoodItem::class)]),
    )
    fun addFoodItem(ctx: Context) {
        val foodItem : FoodItem = jsonToObject(ctx.body())
        val foodItemId = foodItemDao.save(foodItem)
        if (foodItemId != null) {
            foodItem.foodItemId = foodItemId
            ctx.json(foodItem)
            ctx.status(201)
        }
    }

    /**
     * Delete a food item by its ID.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Delete a food item by ID",
        operationId = "deleteFoodItem",
        tags = ["Food"],
        responses = [
            OpenApiResponse("204", [OpenApiContent(FoodItem::class)]),
            OpenApiResponse("404", [OpenApiContent(String::class)])
        ],
        path = "/v1/food-items/{food-item-id}",
        methods = [HttpMethod.DELETE]
    )
    fun deleteFoodItem(ctx: Context) {
        if (foodItemDao.delete(ctx.pathParam("food-item-id").toInt()) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }

    /**
     * Update an existing food item.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Update an existing food item",
        operationId = "updateFoodItem",
        tags = ["Food"],
        responses = [
            OpenApiResponse("204", [OpenApiContent(FoodItem::class)]),
            OpenApiResponse("404", [OpenApiContent(String::class)])
        ],
        path = "/v1/food-items/{food-item-id}",
        methods = [HttpMethod.PATCH],
        requestBody = OpenApiRequestBody([OpenApiContent(FoodItem::class)])
    )
    fun updateFoodItem(ctx: Context) {
        val foundFoodItem : FoodItem = jsonToObject(ctx.body())
        if ((FoodItemController.foodItemDao.update(foodItemId = ctx.pathParam("food-item-id").toInt(), foodItem=foundFoodItem)) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }
}