//package id.finalproject.binar.secondhand.database.dao
//
//import androidx.lifecycle.LiveData
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import id.finalproject.binar.secondhand.model.local.entity.Notification
//
//@Dao
//interface NotificationDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertNotification(notification: Notification)
//
//    @Query("SELECT * FROM notification")
//    fun getNotification(): LiveData<List<Notification>>
//
//    @Query("SELECT * FROM notification WHERE id = :notificationId")
//    fun getNotificationById(notificationId: Int): LiveData<Notification>
//
//}
