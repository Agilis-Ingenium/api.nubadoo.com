package ie.setu.domain

import org.joda.time.DateTime

/**
 * Represents a fitness goal set by a user in the Health Tracker app.
 *
 * Fitness goals define the user's objectives, such as weight loss, muscle gain, or other fitness-related targets.
 *
 * @property goalId The unique identifier for the fitness goal.
 * @property userId The user's identifier associated with the goal.
 * @property goalType The type of fitness goal.
 * Possible values are:
 * - "weight_loss"
 * - "muscle_gain"
 * - "fitness"
 * - "nutrition"
 * - "health"
 * - "other"
 * @property targetValue The target value associated with the goal (e.g., target weight).
 * @property targetDate The date by which the user aims to achieve the fitness goal (nullable).
 *
 * @author Warren Byron (adapted from SETU Msc Computing Agile Dev course content).
 */
data class FitnessGoal (
    var goalId: Int,
    var userId: Int,
    var goalType: String,
    var targetValue: Double,
    var targetDate: DateTime?
)
