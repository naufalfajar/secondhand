package id.finalproject.binar.secondhand.model.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User(
    @PrimaryKey val id: Int? = 0,
    val address: String? = null,
    val city: String? = null,
    val createdAt: String? = null,
    val email: String? = null,
    val full_name: String? = null,
    val image_url: String? = null,
    val password: String? = null,
    val phone_number: Long? = null,
    val updatedAt: String? = null
)