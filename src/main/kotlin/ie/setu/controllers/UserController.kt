package ie.setu.controllers

import ie.setu.domain.FitnessGoal
import io.javalin.http.Context
import ie.setu.utils.jsonToObject
import io.javalin.openapi.*
import ie.setu.domain.repository.UserDAO
import ie.setu.domain.User

/**
 * Controller for handling user-related operations.
 */
object UserController {

    private val userDao = UserDAO()

    /**
     * Gets all users.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Get all users",
        operationId = "getAllUsers",
        tags = ["User"],
        responses = [
            OpenApiResponse("200", [OpenApiContent(Array<User>::class)])
                    ],
        path = "/v1/users",
        methods = [HttpMethod.GET]
    )
    fun getAllUsers(ctx: Context) {
        val users = userDao.getAll()
        if (users.size != 0) {
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
        ctx.json(users)
    }

    /**
     * Gets a user by their user ID.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Get a user by their user ID",
        operationId = "getUserByUserId",
        tags = ["User"],
        responses = [
            OpenApiResponse("200", [OpenApiContent(User::class)]),
            OpenApiResponse("404", [OpenApiContent(String::class)])
        ],
        path = "/v1/users/{user-id}",
        methods = [HttpMethod.GET]
    )
    fun getUserByUserId(ctx: Context) {
        val user = userDao.findById(ctx.pathParam("user-id").toInt())
        if (user != null) {
            ctx.json(user)
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
    }

    /**
     * Adds a new user.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Add a new user",
        operationId = "addUser",
        tags = ["User"],
        requestBody = OpenApiRequestBody([OpenApiContent(User::class)]),
        responses = [OpenApiResponse("201", [OpenApiContent(User::class)])],
        path = "/v1/users",
        methods = [HttpMethod.POST]
    )
    fun addUser(ctx: Context) {
        val user : User = jsonToObject(ctx.body())
        val userId = userDao.save(user)
        if (userId != null) {
            user.userId = userId
            ctx.json(user)
            ctx.status(201)
        }
    }

    /**
     * Gets a user by their email address.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Get a user by their email address",
        operationId = "getUserByEmail",
        tags = ["User"],
        responses = [
            OpenApiResponse("200", [OpenApiContent(User::class)]),
            OpenApiResponse("404", [OpenApiContent(String::class)])
        ],
        path = "/v1/users/email/{email}",
        methods = [HttpMethod.GET]
    )
    fun getUserByEmail(ctx: Context) {
        val user = userDao.findByEmail(ctx.pathParam("email"))
        if (user != null) {
            ctx.json(user)
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
    }

    /**
     * Deletes a user by their user ID.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Delete a user by their user ID",
        operationId = "deleteUser",
        tags = ["User"],
        responses = [
            OpenApiResponse("204", description = "User deleted"),
            OpenApiResponse("404", description = "User not found")
                    ],
        path = "/v1/users/{user-id}",
        methods = [HttpMethod.DELETE]
    )
    fun deleteUser(ctx: Context){
        if (userDao.delete(ctx.pathParam("user-id").toInt()) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }

    /**
     * Updates a user's information by their user ID.
     * @param ctx The Javalin context for handling HTTP requests.
     */
    @OpenApi(
        summary = "Update a user's information by their user ID",
        operationId = "updateUser",
        tags = ["User"],
        requestBody = OpenApiRequestBody([OpenApiContent(User::class)]),
        responses = [
            OpenApiResponse("204", description = "User updated"),
            OpenApiResponse("404", description = "User not found")
                    ],
        path = "/v1/users/{user-id}",
        methods = [HttpMethod.PATCH]
    )
    fun updateUser(ctx: Context){
        val foundUser : User = jsonToObject(ctx.body())
        if ((userDao.update(userId = ctx.pathParam("user-id").toInt(), user=foundUser)) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }
}