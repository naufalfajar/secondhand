package id.finalproject.binar.secondhand.service

import id.finalproject.binar.secondhand.BuildConfig
import id.finalproject.binar.secondhand.model.local.entity.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface SecondHandApi {

    companion object {
        const val BASE_URL = BuildConfig.BASE_URL
    }

    @GET("buyer/product")
    suspend fun getProduct(): List<Product>

    @GET("buyer/product/{id}")
    suspend fun getProductById(@Path("id") productId: Int): Product

}