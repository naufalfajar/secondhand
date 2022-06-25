//package id.finalproject.binar.secondhand.database
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import id.finalproject.binar.secondhand.database.dao.NotificationDao
//import id.finalproject.binar.secondhand.database.entity.Notification
//
//@Database(entities = [Notification::class], version = 1, exportSchema = false)
//abstract class SecondHandDatabase : RoomDatabase() {
//    abstract fun notificationDao(): NotificationDao
//
//    companion object {
//        private var INSTANCE: SecondHandDatabase? = null
//
//        fun getInstance(context: Context): SecondHandDatabase? {
//            if (INSTANCE == null) {
//                synchronized(SecondHandDatabase::class) {
//                    INSTANCE = Room.databaseBuilder(
//                        context.applicationContext,
//                        SecondHandDatabase::class.java, "Data.db"
//                    ).build()
//                }
//            }
//            return INSTANCE
//        }
//
//        fun destroyInstance() {
//            INSTANCE = null
//        }
//    }
//}