package id.finalproject.binar.secondhand.model.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import id.finalproject.binar.secondhand.model.local.entity.Product
import org.json.JSONObject

class ProductConverter {

    @TypeConverter
    fun fromProduct(product: Product?): String {
        return JSONObject().apply {
            put("id", product!!.id)
            put("name", product.name)
            put("base_price", product.base_price)
            put("image_url", product.image_url)
            put("image_name", product.image_name)
            put("location", product.location)
            put("user_id", product.user_id)
            put("status", product.status)
        }.toString()
    }

    @TypeConverter
    fun toProduct(product: String): Product{
        val json = JSONObject(product)
        return Product(
            id = json.get("id") as Int,
            name = json.getString("name"),
            base_price = json.getInt("base_price"),
            image_url = json.getString("image_url"),
            image_name = json.getString("image_name"),
            location = json.getString("location"),
            user_id = json.getInt("user_id"),
            status = json.getString("status")
            )
    }

}