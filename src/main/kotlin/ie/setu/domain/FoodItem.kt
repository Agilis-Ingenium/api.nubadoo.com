package ie.setu.domain

data class FoodItem (
    var foodItemId: Int,
    var name: String,
    var calories: Int,
    var carbohydrates: Double,
    var proteins: Double,
    var fats: Double,
    var vitamins: String?,
    var minerals: String?
)
