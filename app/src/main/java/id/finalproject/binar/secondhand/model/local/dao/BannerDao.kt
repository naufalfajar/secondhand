package id.finalproject.binar.secondhand.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.finalproject.binar.secondhand.model.local.entity.Banner
import kotlinx.coroutines.flow.Flow

@Dao
interface BannerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBanner(banner: List<Banner>)

    @Query("SELECT * FROM banner")
    fun getBanner(): Flow<List<Banner>>

    @Query("SELECT * FROM banner WHERE id = :bannerId")
    suspend fun getBannerById(bannerId: Int): Banner

    @Query("DELETE FROM banner")
    suspend fun deleteBanner()

}