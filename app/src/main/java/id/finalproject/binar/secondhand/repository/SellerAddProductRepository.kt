package id.finalproject.binar.secondhand.repository

import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import id.finalproject.binar.secondhand.model.network.response.Product
import id.finalproject.binar.secondhand.service.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellerAddProductRepository(private val apiService: ApiService) {

    suspend fun postProduct(
        access_token: String,
        name: RequestBody,
        description: RequestBody,
        base_price: RequestBody,
        category_ids: RequestBody,
        location: RequestBody,
        image: MultipartBody.Part
    ) = apiService.postProductSeller(access_token, name, description, base_price, category_ids,location,
        image
    )

    suspend fun getProduct(
        access_token: String
    ) = apiService.getProductSeller(access_token)

    suspend fun getUser(
        access_token: String
    ) = apiService.getUser(access_token)
}