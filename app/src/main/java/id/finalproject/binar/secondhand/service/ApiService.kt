package id.finalproject.binar.secondhand.service

import id.finalproject.binar.secondhand.model.network.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    //AUTH
    @Multipart
    @POST("auth/register")
    suspend fun postRegister(
        @Part("full_name") full_name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("phone_number") phone_number: RequestBody,
        @Part("address") address: RequestBody,
        @Part image: MultipartBody.Part,
        @Part("city") city: RequestBody
    )

    @POST("auth/LoginFragment")
    suspend fun postLogin()

    @GET("auth/user/{id}")
    suspend fun getUserById(@Path("id") userId: Int, @Header("access_token") access_token: String)

    @PUT("auth/user/{id}")
    suspend fun putUserById(@Path("id") userId: Int, @Header("access_token") access_token: String)

    @GET("auth/user")
    suspend fun getUser(@Header("access_token") access_token: String) : Call<GetUserItem>

    //SELLER

    //Banner
    @POST("seller/banner")
    suspend fun postBanner(@Header("access_token") access_token: String)

    @GET("seller/banner")
    suspend fun getBanner(@Header("access_token") access_token: String)

    @GET("seller/banner/{id}")
    suspend fun getBannerById(
        @Path("id") bannerId: Int,
        @Header("access_token") access_token: String
    )

    @DELETE("seller/banner/{id}")
    suspend fun delBannerById(
        @Path("id") bannerId: Int,
        @Header("access_token") access_token: String
    )

    //Category
    @POST("seller/category")
    suspend fun postCategory()

    @GET("seller/category")
    suspend fun getCategory()

    @GET("seller/category/{id}")
    suspend fun getCategoryById(@Path("id") categoryId: Int)

    @DELETE("seller/category/{id}")
    suspend fun deleteCategoryById(@Path("id") categoryId: Int)

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
    )

    @GET("seller/product")
    suspend fun getProductSeller(
        @Header("access_token") access_token: String)

    @GET("seller/product/{id}")
    suspend fun getProductByIdSeller(
        @Path("id") productId: Int,
        @Header("access_token") access_token: String
    )

    @PUT("seller/product/{id}")
    suspend fun putProductByIdSeller(
        @Path("id") productId: Int,
        @Header("access_token") access_token: String
    )

    @DELETE("seller/product/{id}")
    suspend fun deleteProductByIdSeller(
        @Path("id") productId: Int,
        @Header("access_token") access_token: String
    )

    //Order

    @GET("seller/order")
    suspend fun getOrderSeller(@Header("access_token") access_token: String): GetSellerOrder

    @GET("seller/order/{id}")
    suspend fun getOrderByIdSeller(
        @Path("id") orderId: Int,
        @Header("access_token") access_token: String
    ): GetSellerOrderItem

    @PATCH("seller/order/{id}")
    suspend fun patchOrderByIdSeller(
        @Path("id") orderId: Int,
        @Header("access_token") access_token: String
    ): GetSellerOrderItem

    @GET("seller/order/product/{product_id}")
    suspend fun getOrderByProductIdSeller(
        @Path("product_id") productId: Int,
        @Header("access_token") access_token: String
    )

    //BUYER

    //Product

    @GET("buyer/product")
    suspend fun getProductBuyer(
        @Query("status") status: String,
        @Query("category_id") category_id: Int
    )

    @GET("buyer/product/{id}")
    suspend fun getProductByIdBuyer(@Path("id") productId: Int)

    //Order

    @POST("buyer/order")
    suspend fun postOrderBuyer(@Header("access_token") access_token: String)

    @GET("buyer/order")
    suspend fun getOrderBuyer(@Header("access_token") access_token: String)

    @GET("buyer/order/{id}")
    suspend fun getOrderByIdBuyer(
        @Path("id") orderId: Int,
        @Header("access_token") access_token: String
    )

    @PUT("buyer/order/{id}")
    suspend fun putOrderByIdBuyer(
        @Path("id") orderId: Int,
        @Header("access_token") access_token: String
    )

    @DELETE("buyer/order/{id}")
    suspend fun deleteOrderByIdBuyer(
        @Path("id") orderId: Int,
        @Header("access_token") access_token: String
    )

    //HISTORY

    @GET("history")
    suspend fun getHistory(@Header("access_token") access_token: String)

    @GET("history/{id}")
    suspend fun getHistoryById(
        @Path("id") historyId: Int,
        @Header("access_token") access_token: String
    )

    //NOTIFICATION

    @GET("notification")
    suspend fun getNotification(@Header("access_token") access_token: String): GetNotification

    @GET("notification/{id}")
    suspend fun getNotificationById(
        @Path("id") notificationId: Int,
        @Header("access_token") access_token: String
    ): GetNotificationItem

    @PATCH("notification/{id}")
    suspend fun patchNotificationById(
        @Path("id") notificationId: Int,
        @Header("access_token") access_token: String
    ): GetNotificationItem


}