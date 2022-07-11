package id.finalproject.binar.secondhand.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.finalproject.binar.secondhand.model.local.entity.Notification
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: List<Notification>)

    @Query("SELECT * FROM notification")
    fun getNotification(): Flow<List<Notification>>

    @Query("SELECT * FROM notification WHERE id = :notificationId")
    suspend fun getNotificationById(notificationId: Int): Notification

    @Query("DELETE FROM notification")
    suspend fun deleteNotification()

}
