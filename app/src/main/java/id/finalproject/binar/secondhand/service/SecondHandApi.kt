package id.finalproject.binar.secondhand.service

import id.finalproject.binar.secondhand.BuildConfig
import id.finalproject.binar.secondhand.model.local.entity.Banner
import id.finalproject.binar.secondhand.model.local.entity.Category
import id.finalproject.binar.secondhand.model.local.entity.Notification
import id.finalproject.binar.secondhand.model.local.entity.Product
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface SecondHandApi {

    companion object {
        const val BASE_URL = BuildConfig.BASE_URL
    }

    //Home List Product

    @GET("buyer/product")
    suspend fun getProduct(): List<Product>

    @GET("buyer/product/{id}")
    suspend fun getProductById(@Path("id") productId: Int): Product

    //Home Category

    @GET("seller/category")
    suspend fun getCategory(): List<Category>

    @GET("seller/category/{id}")
    suspend fun getCategoryById(@Path("id") categoryId: Int): Category

    //Home Banner

    @GET("seller/banner")
    suspend fun getBanner(): List<Banner>

    @GET("seller/banner/{id}")
    suspend fun getBannerById(@Path("id") bannerId: Int): Banner

    //Notification

    @GET("notification")
    suspend fun getNotification(@Header("access_token") access_token: String): List<Notification>

    @GET("notification/{id}")
    suspend fun getNotificationById(
        @Path("id") notificationId: Int,
        @Header("access_token") access_token: String
    ): Notification

}