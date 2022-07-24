package id.finalproject.binar.secondhand.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.finalproject.binar.secondhand.model.local.entity.History
import id.finalproject.binar.secondhand.model.local.entity.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: List<History>)

    @Query("SELECT * FROM history")
    fun getHistory(): Flow<List<History>>

    @Query("DELETE FROM history")
    suspend fun deleteHistory()

}