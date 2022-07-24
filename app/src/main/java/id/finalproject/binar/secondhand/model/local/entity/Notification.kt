package id.finalproject.binar.secondhand.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import id.finalproject.binar.secondhand.model.local.converters.ProductConverter

@Entity(tableName = "notification")
data class Notification(
    @PrimaryKey val id: Int? = 0,
    val bid_price: Int? = 0,
    val buyer_name: String? = null,
    val created_at: String? = null,
    val image_url: String? = null,
    @TypeConverters(ProductConverter::class)
    val Product: Product? = null,
    val product_id: Int? = 0,
    val product_name: String? = null,
    val read: Boolean? = false,
    val receiver_id: Int? = 0,
    val seller_name: String? = null,
    val status: String? = null,
    val transaction_date: String? = null,
    val updated_at: String? = null
)