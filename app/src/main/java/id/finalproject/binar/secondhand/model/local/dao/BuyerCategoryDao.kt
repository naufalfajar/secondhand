//package id.finalproject.binar.secondhand.model.local.dao
//
//import androidx.lifecycle.LiveData
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import id.finalproject.binar.secondhand.model.local.entity.BuyerCategory
//
//@Dao
//interface BuyerCategoryDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertCategory(buyerCategory: BuyerCategory)
//
//    @Query("SELECT * FROM buyer_category")
//    fun getCategory(): LiveData<List<BuyerCategory>>
//
//}