package id.finalproject.binar.secondhand.model.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import id.finalproject.binar.secondhand.model.local.entity.Category

class CategoryConverters {

//    @TypeConverter
//    fun fromCategory(category: Category): String {
//        return JSONObject().apply {
//            put("id", category.id)
//            put("name", category.name)
//        }.toString()
//    }
//
//    @TypeConverter
//    fun toCategory(category: String): List<Category> {
//        val gson = Gson()
//        val type =
//            object : TypeToken<List<Category?>?>() {}.type
//        return gson.fromJson(category, type)
//
//    }

//    @TypeConverter
//    fun fromCategory(category: Category): String {
//        return JSONObject().apply {
//            put("id", category.id)
//            put("name", category.name)
//        }.toString()
//    }
//
//    @TypeConverter
//    fun toCategory(category: String): Category {
//        val json = JSONObject(category)
//        return Category(json.get("id") as Int, json.getString("name"))
//    }

    @TypeConverter
    fun listToJson(value: List<Category>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Category>::class.java).toList()

//    @TypeConverter
//    fun toCategories(value: String?): Categories {
//        if (value == null || value.isEmpty()) {
//            return Categories()
//        }
//
//        val list: List<String> = value.split(",")
//        val longList = ArrayList<Long>()
//        for (item in list) {
//            if (!item.isEmpty()) {
//                longList.add(item.toLong())
//            }
//        }
//        return Categories(longList)
//    }
//
//    @TypeConverter
//    fun toString(categories: Categories?): String {
//
//        var string = ""
//
//        if (categories == null) {
//            return string
//        }
//
//        categories.categories.forEach {
//            string += "$it,"
//        }
//        return string
//    }


}