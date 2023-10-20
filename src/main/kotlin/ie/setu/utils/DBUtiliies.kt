package ie.setu.utils

import ie.setu.domain.User
import ie.setu.domain.db.Users
import ie.setu.domain.Activity
import ie.setu.domain.db.Activities
import ie.setu.domain.FoodItem
import ie.setu.domain.db.FoodItems
import org.jetbrains.exposed.sql.ResultRow

fun mapToUser(it: ResultRow) = User(
    userId = it[Users.userId],
    username = it[Users.username],
    email = it[Users.email],
    password = it[Users.password],
    firstName = it[Users.firstName],
    lastName = it[Users.lastName],
    dateOfBirth = it[Users.dateOfBirth],
    gender = it[Users.gender],
    registrationDate = it[Users.registrationDate]
)

fun mapToActivity(it: ResultRow) = Activity(
    activityId = it[Activities.activityId],
    userId = it[Activities.userId],
    activityType = it[Activities.activityType],
    durationMinutes = it[Activities.durationMinutes],
    distanceKm = it[Activities.distanceKM],
    workoutIntensity = it[Activities.workoutIntensity],
    activityDate = it[Activities.activityDate]
)

fun mapToFoodItem(it: ResultRow) = FoodItem(
    foodId = it[FoodItems.foodId],
    name = it[FoodItems.name],
    calories = it[FoodItems.calories],
    carbohydrates = it[FoodItems.carbohydrates],
    proteins = it[FoodItems.proteins],
    fats = it[FoodItems.fats],
    vitamins = it[FoodItems.vitamins],
    minerals = it[FoodItems.minerals]
)