package ie.setu.helpers

import ie.setu.domain.*
import org.joda.time.DateTime

val nonExistingEmail = "112233445566778testUser@xxxxx.xx"
val validName = "Test User 1"
val validEmail = "testuser1@test.com"
val updatedName = "Updated Name"
val updatedEmail = "Updated Email"

val updatedDescription = "Updated Description"
val updatedDuration = 30.0
val updatedCalories = 945
val updatedStarted = DateTime.parse("2020-06-11T05:59:27.258Z")

val users = arrayListOf<User>(
    User(userId = 1, username = "mary_jones", email = "mary@example.com", password = "secure123", firstName = "Mary", lastName = "Jones", registrationDate = DateTime.now(), dateOfBirth = DateTime.now(), gender = "female",),
    User(userId = 2, username = "peter_wilson", email = "peter@example.com", password = "pass1234", firstName = "Peter", lastName = "Wilson", registrationDate = DateTime.now(), dateOfBirth = DateTime.now(), gender = "male"),
    User(userId = 3, username = "john_doe", email = "john@example.com", password = "password123", firstName = "John", lastName = "Doe", registrationDate = DateTime.now(), dateOfBirth = DateTime.now(), gender = "male"),
    User(userId = 4, username = "jane_smith", email = "jane@example.com", password = "secret456", firstName = "Jane", lastName = "Smith", registrationDate = DateTime.now(), dateOfBirth = DateTime.now(), gender = "female")
)

val workoutplans = arrayListOf<WorkoutPlan>(
    WorkoutPlan(planId = 1, userId = 1, planName = "Plan 1", schedule = "Monday, Wednesday, Friday", planDate = DateTime(2023, 10, 28, 15, 0, 0), content="", description="This is a description", duration="4 weeks", goal="weight loss"),
    WorkoutPlan(planId = 2, userId = 1, planName = "Plan 2", schedule = "Tuesday, Thursday", planDate = DateTime(2023, 10, 29, 15, 0, 0), content="", description="This is a description", duration="4 weeks", goal="weight loss"),
    WorkoutPlan(planId = 3, userId = 3, planName = "Plan 3", schedule = "Saturday", planDate = DateTime(2023, 10, 30, 15, 0, 0), content="", description="This is a description", duration="4 weeks", goal="weight loss")
)

val fooditems = arrayListOf<FoodItem>(
    FoodItem(foodItemId = 1, "Broccoli", 55, 11.2, 3.7, 0.6, "Vitamin C, Vitamin K", "Folate, Potassium"),
    FoodItem(foodItemId = 2,"Spinach", 23, 3.6, 2.9, 0.4, "Vitamin A, Vitamin C", "Iron, Folate"),
    FoodItem(foodItemId = 3,"Eggs", 68, 0.6, 5.5, 4.8, "Vitamin D, Vitamin B12", "Choline, Selenium"),
    FoodItem(foodItemId = 4,"Brown Rice", 216, 44.8, 5.0, 1.6, "Thiamin, Niacin", "Magnesium, Phosphorus"),
    FoodItem(foodItemId = 5,"Banana", 105, 27.0, 1.3, 0.4, "Vitamin C, Vitamin B6", "Potassium, Manganese")
)

val activities = arrayListOf<Activity>(
    Activity(activityId = 1, userId = 1, activityType = "yoga", durationMinutes = 30, distanceKm = 2.0, workoutIntensity = "low", activityDate = DateTime.parse("2023-01-01")),
    Activity(activityId = 2, userId = 1, activityType = "running", durationMinutes = 45, distanceKm = 5.2, workoutIntensity = "moderate", activityDate = DateTime.now()),
    Activity(activityId = 3, userId = 1, activityType = "swimming", durationMinutes = 60, distanceKm = 1.5, workoutIntensity ="high", activityDate = DateTime.now())
)

val fitnessgoals = arrayListOf<FitnessGoal>(
    FitnessGoal(goalId = 1, userId = 1, goalType = "weight", targetValue = 10.0, targetDate = DateTime.now(), achieved = false),
    FitnessGoal(goalId = 2, userId = 1, goalType = "muscle", targetValue = 5.0, targetDate = DateTime.now(), achieved = false),
    FitnessGoal(goalId = 3, userId = 1, goalType = "fitness", targetValue = 10.0, targetDate = DateTime.now(), achieved = false)
)

val meals = arrayListOf<MealLog>(
    MealLog(logId = 1, userId = 1, mealTime = DateTime.now(), totalCalories = 450, foodItems = fooditems),
    MealLog(logId = 2, userId = 1, mealTime = DateTime.now(), totalCalories = 480, foodItems = fooditems),
    MealLog(logId = 3, userId = 1, mealTime = DateTime.now(), totalCalories = 620, foodItems = fooditems)
)

val metrics = arrayListOf<Metric>(
    Metric(metricId = 1, userId = 1, weight = 180.5, height = 175.0, bmi = 24.6, systolicBloodPressure = 120, diastolicBloodPressure = 80, heartRate = 72, bloodSugar = 95.5, sleepDuration = 7, sleepQuality = "good", createdAt = DateTime.now()),
    Metric(metricId = 2, userId = 1, weight = 134.0, height = 211.0, bmi = 25.6, systolicBloodPressure = 112, diastolicBloodPressure = 90, heartRate = 34, bloodSugar = 95.5, sleepDuration = 7, sleepQuality = "very good", createdAt = DateTime.now()),
    Metric(metricId = 3, userId = 1, weight = 86.3, height = 145.0, bmi = 26.6, systolicBloodPressure = 80, diastolicBloodPressure = 140, heartRate = 5, bloodSugar = 95.5, sleepDuration = 7, sleepQuality = "poor", createdAt = DateTime.now()),
)