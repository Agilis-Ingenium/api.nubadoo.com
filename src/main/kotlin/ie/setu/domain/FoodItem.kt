package ie.setu.domain

data class FoodItem (
    var foodId: Int,
    var name: String,
    var calories: Int,
    var carbohydrates: Double,
    var proteins: Double,
    var fats: Double,
    var vitamins: Double?,
    var minerals: String?
)
