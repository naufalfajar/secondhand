package id.finalproject.binar.secondhand.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(

    @PrimaryKey val id: Int? = 0,
    val base_price: Int,
    val categories: String? = null,
    val createdAt: String? = null,
    val description: String? = null,
    val image_name: String? = null,
    val image_url: String? = null,
    val location: String? = null,
    val name: String? = null,
    val status: String? = null,
    val updatedAt: String? = null,
    val user_id: Int? = 0
)