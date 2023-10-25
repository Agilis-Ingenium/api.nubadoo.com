package ie.setu.domain.repository

import ie.setu.domain.User
import ie.setu.domain.db.Users
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ie.setu.utils.mapToUser
import ie.setu.utils.setGenderEnum
import ie.setu.utils.retrieveGenderEnum
import org.jetbrains.exposed.sql.*
import java.sql.ResultSet

class UserDAO {

    fun getAll(): ArrayList<User> {
        val userList: ArrayList<User> = arrayListOf()
        transaction {
            Users.selectAll().map {
                userList.add(mapToUser(it)) }
        }
        return userList
    }

    fun findById(id: Int): User?{
        return transaction {
            Users.select() {
                Users.userId eq id}
                .map{mapToUser(it)}
                .firstOrNull()
        }
    }

    fun save(user: User){
        transaction {
            Users.insert {
                it[username] = user.username
                it[email] = user.email
                it[password] = user.password
                it[firstName] = user.firstName
                it[lastName] = user.lastName
                //it[dateOfBirth] = user.dateOfBirth
                //it[gender] = user.gender
                //it[registrationDate] = user.registrationDate
            }
        }
    }

    fun findByEmail(email: String) :User?{
        return transaction {
            Users.select() {
                Users.email eq email}
                .map{mapToUser(it)}
                .firstOrNull()
        }
    }

    fun delete(userId: Int):Int{
        return transaction{
            Users.deleteWhere{
                Users.userId eq userId
            }
        }
    }

    fun update(userId: Int, user: User){
        transaction {
            Users.update ({
                Users.userId eq userId}) {
                it[username] = user.username
                it[email] = user.email
            }
        }
    }
}