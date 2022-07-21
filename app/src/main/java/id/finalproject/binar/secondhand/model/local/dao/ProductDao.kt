package id.finalproject.binar.secondhand.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.finalproject.binar.secondhand.model.local.entity.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: List<Product>)

    @Query("SELECT * FROM product")
    fun getProduct(): Flow<List<Product>>

    @Query("SELECT * FROM product WHERE id = :productId")
    fun getProductById(productId: Int): Product

    @Query("DELETE FROM product")
    suspend fun deleteProduct()

}