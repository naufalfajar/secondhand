package id.finalproject.binar.secondhand.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import id.finalproject.binar.secondhand.model.local.entity.BuyerCategory

@Dao
interface BuyerCategoryDao {

    @Query("SELECT * FROM buyer_category")
    fun getCategory(): LiveData<List<BuyerCategory>>

}