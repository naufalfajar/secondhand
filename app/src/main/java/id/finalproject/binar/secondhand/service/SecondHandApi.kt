package id.finalproject.binar.secondhand.service

import id.finalproject.binar.secondhand.BuildConfig
import id.finalproject.binar.secondhand.model.local.entity.Banner
import id.finalproject.binar.secondhand.model.local.entity.Category
import id.finalproject.binar.secondhand.model.local.entity.Notification
import id.finalproject.binar.secondhand.model.local.entity.Product
import id.finalproject.binar.secondhand.model.network.response.PostBuyerOrderResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import id.finalproject.binar.secondhand.model.local.entity.*
import id.finalproject.binar.secondhand.model.network.response.GetUserItem
import okhttp3.MultipartBody

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

    //Product
    @Multipart
    @POST("seller/product")
    suspend fun postProductSeller(
        @Header("access_token") access_token: String,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("base_price") base_price: RequestBody,
        @Part("category_ids") category_ids: RequestBody,
        @Part("location") location: RequestBody,
        @Part image: MultipartBody.Part
    ) : Response<PostProductResponse>

    @GET("auth/user")
    suspend fun getUser(
        @Header("access_token") access_token: String) : GetUserItem
    //Sell List Product
    @GET("seller/product")
    suspend fun getProductSeller(@Header("access_token") access_token: String): List<ProductSeller>

    @Multipart
    @POST("buyer/order")
    suspend fun postOrderBuyer(
        @Header("access_token") access_token: String,
        @Part("product_id") product_id: RequestBody,
        @Part("bid_price") bid_price: RequestBody
    ) : Response<PostBuyerOrderResponse>
}