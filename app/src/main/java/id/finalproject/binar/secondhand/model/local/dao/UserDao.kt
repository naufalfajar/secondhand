package id.finalproject.binar.secondhand.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.finalproject.binar.secondhand.model.local.entity.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: List<Category>)

    @Query("SELECT * FROM category")
    fun getCategory(): Flow<List<Category>>

    @Query("SELECT * FROM category WHERE id = :categoryId")
    suspend fun getCategoryById(categoryId: Int): Category

    @Query("DELETE FROM category")
    suspend fun deleteCategory()

}