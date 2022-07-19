package id.finalproject.binar.secondhand.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.finalproject.binar.secondhand.model.local.entity.ProductSeller
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductSellerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: List<ProductSeller>)

    @Query("SELECT * FROM product_seller")
    fun getProduct(): Flow<List<ProductSeller>>

    @Query("DELETE FROM product_seller")
    suspend fun deleteProduct()

}