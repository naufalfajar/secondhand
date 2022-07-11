package id.finalproject.binar.secondhand.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "banner")
data class Banner(
    @PrimaryKey val id: Int? = 0,
    val createdAt: String? = null,
    val image_url: String? = null,
    val name: String? = null,
    val updatedAt: String? = null
)