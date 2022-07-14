package id.finalproject.binar.secondhand.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.finalproject.binar.secondhand.model.local.converters.CategoryConverters
import id.finalproject.binar.secondhand.model.local.dao.BannerDao
import id.finalproject.binar.secondhand.model.local.dao.CategoryDao
import id.finalproject.binar.secondhand.model.local.dao.NotificationDao
import id.finalproject.binar.secondhand.model.local.dao.ProductDao
import id.finalproject.binar.secondhand.model.local.entity.Banner
import id.finalproject.binar.secondhand.model.local.entity.Category
import id.finalproject.binar.secondhand.model.local.entity.Notification
import id.finalproject.binar.secondhand.model.local.entity.Product

@Database(
    entities = [Product::class, Category::class, Banner::class, Notification::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(CategoryConverters::class)
abstract class SecondHandDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun bannerDao(): BannerDao
    abstract fun notificationDao(): NotificationDao

}