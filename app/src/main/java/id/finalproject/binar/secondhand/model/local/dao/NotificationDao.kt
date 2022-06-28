//package id.finalproject.binar.secondhand.database.dao
//
//import androidx.lifecycle.LiveData
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import id.finalproject.binar.secondhand.database.entity.Notification
//
//@Dao
//interface NotificationDao {
//
//    @Query("SELECT * FROM notification")
//    fun getNotification(): LiveData<List<Notification>>
//
//    @Query("SELECT * FROM notification WHERE id = :notificationId")
//    fun getNotificationById(notificationId: Int): LiveData<Notification>
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insertNotification(movies: List<Notification>)
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insertNOtif(movie: Notification)
//
//    @Query("DELETE FROM notification")
//    suspend fun deleteNotification()
//
//}
