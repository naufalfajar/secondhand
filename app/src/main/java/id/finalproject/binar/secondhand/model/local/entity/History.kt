package id.finalproject.binar.secondhand.model.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class History(
    @PrimaryKey val id: Int? = 0,
    val category: String? = null,
    val image_url: String? = null,
    val price: Int? = 0,
    val product_name: String? = null,
    val status: String? = null,
    val transaction_date: String? = null,
    val user_id: Int? = 0
)