//package id.finalproject.binar.secondhand.model.local.converters
//
//import androidx.room.ProvidedTypeConverter
//import androidx.room.TypeConverter
//import com.google.gson.Gson
//import com.google.gson.GsonBuilder
//import com.google.gson.reflect.TypeToken
//import id.finalproject.binar.secondhand.model.local.entity.Category
//import org.json.JSONArray
//import org.json.JSONObject
//import java.lang.reflect.Type
//
//class BuyerCategoryTypeConverters {
//    @TypeConverter
//    fun fromCategory(category: Category): String {
//        return JSONObject().apply {
//            put("id", category.id)
//            put("name", category.name)
//        }.toString()
//    }
//
////    @TypeConverter
////    fun toCategory(category: String): List<Category> {
////        val gson = Gson()
////        val type: Type = object : TypeToken<List<Category?>?>() {}.type
////        return gson.toJson(Category, type)
////    }
//}