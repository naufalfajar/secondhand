package id.finalproject.binar.secondhand.model.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notification")
data class Notification(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = 0,

    @ColumnInfo(name = "bid_price")
    val bidPrice: Int = 0,

    @ColumnInfo(name = "buyer_name")
    val buyerName: String? = null,

    @ColumnInfo(name = "createdAt")
    val createdAt: String? = null,

    @ColumnInfo(name = "image_url")
    val imageUrl: String? = null,

    @ColumnInfo(name = "product_id")
    val productId: Int? = 0,

    @ColumnInfo(name = "read")
    val read: Boolean? = false,

    @ColumnInfo(name = "receiver_id")
    val receiverId: Int? = 0,

    @ColumnInfo(name = "seller_name")
    val sellerName: String? = null,

    @ColumnInfo(name = "status")
    val status: String? = null,

    @ColumnInfo(name = "transaction_date")
    val transactionDate: String? = null,

    @ColumnInfo(name = "updatedAt")
    val updatedAt: String? = null
)