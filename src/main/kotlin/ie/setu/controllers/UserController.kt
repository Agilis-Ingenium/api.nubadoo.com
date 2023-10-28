package ie.setu.controllers

import io.javalin.http.Context
import ie.setu.utils.jsonToObject

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
    fun updateUser(ctx: Context){
        val foundUser : User = jsonToObject(ctx.body())
        if ((userDao.update(userId = ctx.pathParam("user-id").toInt(), user=foundUser)) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }
}