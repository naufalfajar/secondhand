//package id.finalproject.binar.secondhand.model.local.converters
//
//import androidx.room.TypeConverter
//import id.finalproject.binar.secondhand.model.local.entity.BuyerCategory
//import org.json.JSONObject
//
//object BuyerCategoryTypeConverters {
//    @TypeConverter
//    fun fromBuyerCategory(category: BuyerCategory): String {
//        return JSONObject().apply {
//            put("id", category.id)
//            put("name", category.name)
//        }.toString()
//    }
//
//    @TypeConverter
//    fun toBuyerCategory(category: String): BuyerCategory {
//        val json = JSONObject(category)
//        return BuyerCategory(json.getInt("id"), json.getString("name"))
//    }
//}