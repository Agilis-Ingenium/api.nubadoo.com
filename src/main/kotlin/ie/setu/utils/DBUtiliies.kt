package ie.setu.utils

import ie.setu.domain.*
import org.jetbrains.exposed.sql.ResultRow
import ie.setu.domain.db.Users
import ie.setu.domain.db.Activities
import ie.setu.domain.db.FoodItems
import ie.setu.domain.db.FitnessGoals
import ie.setu.domain.db.MealLogs
import ie.setu.domain.db.MealLogFoodItems
import ie.setu.domain.db.Metrics
import ie.setu.domain.db.WorkoutPlans
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Maps a ResultRow to a User object.
 * @param it The ResultRow to be mapped.
 * @return A User object.
 */
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

/**
 * Maps a ResultRow to an Activity object.
 * @param it The ResultRow to be mapped.
 * @return An Activity object.
 */
fun mapToActivity(it: ResultRow) = Activity(
    activityId = it[Activities.activityId],
    userId = it[Activities.userId],
    activityType = it[Activities.activityType],
    durationMinutes = it[Activities.durationMinutes],
    distanceKm = it[Activities.distanceKM],
    workoutIntensity = it[Activities.workoutIntensity],
    activityDate = it[Activities.activityDate]
)

/**
 * Maps a ResultRow to a FoodItem object.
 * @param it The ResultRow to be mapped.
 * @return A FoodItem object.
 */
fun mapToFoodItem(it: ResultRow) = FoodItem(
    foodItemId = it[FoodItems.foodItemId],
    name = it[FoodItems.name],
    calories = it[FoodItems.calories],
    carbohydrates = it[FoodItems.carbohydrates],
    proteins = it[FoodItems.proteins],
    fats = it[FoodItems.fats],
    vitamins = it[FoodItems.vitamins],
    minerals = it[FoodItems.minerals]
)

/**
 * Maps a ResultRow to a FitnessGoal object.
 * @param it The ResultRow to be mapped.
 * @return A FitnessGoal object.
 */
fun mapToFitnessGoal(it: ResultRow) = FitnessGoal(
    goalId = it[FitnessGoals.goalId],
    userId = it[FitnessGoals.userId],
    targetValue = it[FitnessGoals.targetValue],
    targetDate = it[FitnessGoals.targetDate],
    goalType = it[FitnessGoals.goalType],
    achieved = it[FitnessGoals.achieved]
)

/**
 * Maps a database row [it] to a [MealLog] object.
 *
 * @param it The [ResultRow] representing a row from the database.
 * @return A [MealLog] object populated with data from the database row.
 */
fun mapToMealLog(it: ResultRow): MealLog {
    val logId = it[MealLogs.logId]
    val userId = it[MealLogs.userId]
    val mealTime = it[MealLogs.mealTime]
    val totalCalories = it[MealLogs.totalCalories]

    val foodItems = retrieveFoodItemsByLogId(logId)
    //println(foodItems)

    return MealLog(logId, userId, mealTime, totalCalories, foodItems)
}

/**
 * Retrieves a list of [FoodItem]s associated with a specific meal log [logId].
 *
 * @param logId The ID of the meal log for which food items are to be retrieved.
 * @return A list of [FoodItem]s associated with the specified meal log.
 */
fun retrieveFoodItemsByLogId(logId: Int): List<FoodItem> {
    return transaction {
        (MealLogFoodItems innerJoin FoodItems)
            .slice(FoodItems.foodItemId,
                FoodItems.name,
                FoodItems.calories,
                FoodItems.carbohydrates,
                FoodItems.proteins,
                FoodItems.fats,
                FoodItems.vitamins,
                FoodItems.minerals)
            .select { MealLogFoodItems.mealLogId eq logId }
            .map { row -> FoodItem(
                row[FoodItems.foodItemId],
                row[FoodItems.name],
                row[FoodItems.calories],
                row[FoodItems.carbohydrates],
                row[FoodItems.proteins],
                row[FoodItems.fats],
                row[FoodItems.vitamins],
                row[FoodItems.minerals]
            )
        }
    }
}

/**
 * Maps a ResultRow to a Metric object.
 * @param it The ResultRow to be mapped.
 * @return A Metric object.
 */
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

/**
 * Maps a ResultRow to a WorkoutPlan object.
 * @param it The ResultRow to be mapped.
 * @return A WorkoutPlan object.
 */
fun mapToWorkoutPlan(it: ResultRow) = WorkoutPlan(
    planId = it[WorkoutPlans.planId],
    userId = it[WorkoutPlans.userId],
    planName = it[WorkoutPlans.planName],
    schedule = it[WorkoutPlans.schedule],
    planDate = it[WorkoutPlans.planDate],
    goal = it[WorkoutPlans.goal],
    duration = it[WorkoutPlans.duration],
    description = it[WorkoutPlans.description],
    content = it[WorkoutPlans.content]
)