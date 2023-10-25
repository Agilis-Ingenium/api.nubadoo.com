package ie.setu.utils
import org.jetbrains.exposed.sql.ResultRow

import ie.setu.domain.User
import ie.setu.domain.db.Users
import ie.setu.domain.Activity
import ie.setu.domain.db.Activities
import ie.setu.domain.FoodItem
import ie.setu.domain.db.FoodItems
import ie.setu.domain.FitnessGoal
import ie.setu.domain.db.FitnessGoals
import ie.setu.domain.MealLog
import ie.setu.domain.db.MealLogs
import ie.setu.domain.MealLogFoodItem
import ie.setu.domain.db.MealLogFoodItems
import ie.setu.domain.Metric
import ie.setu.domain.db.Metrics
import ie.setu.domain.WorkoutPlan
import ie.setu.domain.db.WorkoutPlans

fun mapToUser(it: ResultRow) = User(
    userId = it[Users.userId],
    username = it[Users.username],
    email = it[Users.email],
    password = it[Users.password],
    firstName = it[Users.firstName],
    lastName = it[Users.lastName],
    //dateOfBirth = it[Users.dateOfBirth],
    //gender = it[Users.gender],
    //registrationDate = it[Users.registrationDate]
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

fun mapToFitnessGoal(it: ResultRow) = FitnessGoal(
    goalId = it[FitnessGoals.goalId],
    userId = it[FitnessGoals.userId],
    targetValue = it[FitnessGoals.targetValue],
    targetDate = it[FitnessGoals.targetDate],
    goalType = it[FitnessGoals.goalType]
)

fun mapToMealLog(it: ResultRow) = MealLog(
    logId = it[MealLogs.logId],
    userId = it[MealLogs.userId],
    mealTime = it[MealLogs.mealTime],
    totalCalories = it[MealLogs.totalCalories]
)

fun mapToMealLogFoodItem(it: ResultRow) = MealLogFoodItem(
    mealLogId = it[MealLogFoodItems.mealLogId],
    foodId = it[MealLogFoodItems.foodId],
    quantity = it[MealLogFoodItems.quantity],
    calories = it[MealLogFoodItems.calories]
)

fun mapToMetric(it: ResultRow) = Metric(
    metricId = it[Metrics.metricId],
    userId = it[Metrics.userId],
    weight = it[Metrics.weight],
    height = it[Metrics.height],
    bmi = it[Metrics.bmi],
    systolicBloodPressure = it[Metrics.systolicBloodPressure],
    diastolicBloodPressure = it[Metrics.diastolicBloodPressure],
    heartRate = it[Metrics.heartRate],
    bloodSugar = it[Metrics.bloodSugar],
    sleepDuration = it[Metrics.sleepDuration],
    sleepQuality = it[Metrics.sleepQuality],
    createdAt = it[Metrics.createdAt]
)

fun mapToWorkoutPlan(it: ResultRow) = WorkoutPlan(
    planId = it[WorkoutPlans.planId],
    userId = it[WorkoutPlans.userId],
    planName = it[WorkoutPlans.planName],
    schedule = it[WorkoutPlans.schedule],
    planDate = it[WorkoutPlans.planDate]
)
