package id.finalproject.binar.secondhand.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import id.finalproject.binar.secondhand.model.local.entity.BuyerProduct

@Dao
interface BuyerProductDao {

    @Query("SELECT * FROM buyer_product")
    fun getProductBuyer(): LiveData<List<BuyerProduct>>

    @Query("SELECT * FROM notification WHERE id = :productId")
    fun getProductById(productId: Int): LiveData<BuyerProduct>

}