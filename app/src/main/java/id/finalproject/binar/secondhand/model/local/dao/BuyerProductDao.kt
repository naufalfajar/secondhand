//package id.finalproject.binar.secondhand.model.local.dao
//
//import androidx.lifecycle.LiveData
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import id.finalproject.binar.secondhand.model.local.entity.BuyerProduct
//
//@Dao
//interface BuyerProductDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertProductBuyer(buyerProduct: BuyerProduct)
//
//    @Query("SELECT * FROM buyer_product")
//    fun getProductBuyer(): LiveData<List<BuyerProduct>>
//
//    @Query("SELECT * FROM notification WHERE id = :productId")
//    fun getProductById(productId: Int): LiveData<BuyerProduct>
//
//}