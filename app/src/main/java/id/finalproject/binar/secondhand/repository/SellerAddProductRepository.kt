package id.finalproject.binar.secondhand.repository

import androidx.core.app.NotificationCompat.getCategory
import androidx.room.withTransaction
import id.finalproject.binar.secondhand.model.local.SecondHandDatabase
import id.finalproject.binar.secondhand.model.network.response.GetUserItem
import id.finalproject.binar.secondhand.model.network.response.seller.PostProductResponse
import id.finalproject.binar.secondhand.service.ApiService
import id.finalproject.binar.secondhand.service.SecondHandApi
import id.finalproject.binar.secondhand.util.networkBoundResource
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class SellerAddProductRepository @Inject constructor(
    private val apiService: SecondHandApi,
    private val db: SecondHandDatabase
) {
    private val categoryDao = db.categoryDao()

    suspend fun postProduct(
        access_token: String,
        name: RequestBody,
        description: RequestBody,
        base_price: RequestBody,
        category_ids: RequestBody,
        location: RequestBody,
        image: MultipartBody.Part
    ) : retrofit2.Response<PostProductResponse> {
        return apiService.postProductSeller(access_token, name, description, base_price, category_ids,location, image)
    }

    suspend fun getUser(
        access_token: String
    ): GetUserItem = apiService.getUser(access_token)

    fun getAllCategory() = networkBoundResource(
        query = { categoryDao.getCategory() },
        fetch = { apiService.getCategory() },
        saveFetchResult = { category ->
            db.withTransaction {
                categoryDao.deleteCategory()
                categoryDao.insertCategory(category)
            }
        }
    )
}