package ie.setu.domain.repository

import ie.setu.domain.User
import ie.setu.domain.db.Users
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import ie.setu.utils.mapToUser

/**
 * Data Access Object (DAO) for managing user-related data in the Health Tracker app.
 *
 * This class provides methods to retrieve, create, update, and delete user data in the database.
 *
 * @author Warren Byron (adapted from SETU Msc Computing Agile Dev course content).
 */
class UserDAO {

    /**
     * Retrieves a list of all users from the database.
     *
     * @return An ArrayList of [User] objects representing all users.
     */
    fun getAll(): ArrayList<User> {
        val userList: ArrayList<User> = arrayListOf()
        transaction {
            Users.selectAll().map {
                userList.add(mapToUser(it)) }
        }
        return userList
    }

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id The unique identifier of the user to retrieve.
     * @return A [User] object representing the user with the specified ID, or null if not found.
     */
    fun findById(id: Int): User?{
        return transaction {
            Users.select() {
                Users.userId eq id}
                .map{mapToUser(it)}
                .firstOrNull()
        }
    }

    /**
     * Saves a new user to the database.
     *
     * @param user The [User] object to be saved.
     * @return The unique identifier (userId) of the newly saved user.
     */
    fun save(user: User) : Int? {
        return transaction {
            Users.insert {
                it[username] = user.username
                it[email] = user.email
                it[password] = user.password
                it[firstName] = user.firstName
                it[lastName] = user.lastName
                it[dateOfBirth] = user.dateOfBirth
                it[gender] = user.gender
                it[registrationDate] = user.registrationDate
            } get Users.userId
        }
    }

    /**
     * Retrieves a user by their email address.
     *
     * @param email The email address of the user to retrieve.
     * @return A [User] object representing the user with the specified email, or null if not found.
     */
    fun findByEmail(email: String) :User?{
        return transaction {
            Users.select() {
                Users.email eq email}
                .map{mapToUser(it)}
                .firstOrNull()
        }
    }

    /**
     * Deletes a user from the database by their unique identifier.
     *
     * @param userId The unique identifier of the user to delete.
     * @return The number of rows affected by the deletion operation.
     */
    fun delete(userId: Int):Int{
        return transaction{
            Users.deleteWhere{
                Users.userId eq userId
            }
        }
    }

    /**
     * Updates an existing user in the database.
     *
     * @param userId The unique identifier of the user to update.
     * @param user The updated [User] object.
     * @return The number of rows affected by the update operation.
     */
    fun update(userId: Int, user: User) : Int {
        return transaction {
            Users.update ({
                Users.userId eq userId}) {
                it[username] = user.username
                it[email] = user.email
                it[password] = user.password
                it[firstName] = user.firstName
                it[lastName] = user.lastName
                it[dateOfBirth] = user.dateOfBirth
                it[gender] = user.gender
                it[registrationDate] = user.registrationDate
            }
        }
    }
}