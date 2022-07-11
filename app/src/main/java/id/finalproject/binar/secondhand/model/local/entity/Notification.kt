package id.finalproject.binar.secondhand.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notification")
data class Notification(
    @PrimaryKey val id: Int? = 0,
    val bid_price: Int = 0,
    val buyer_name: String? = null,
    val createdAt: String? = null,
    val image_url: String? = null,
    val product_id: Int? = 0,
    val read: Boolean? = false,
    val receiver_id: Int? = 0,
    val seller_name: String? = null,
    val status: String? = null,
    val transaction_date: String? = null,
    val updatedAt: String? = null
)