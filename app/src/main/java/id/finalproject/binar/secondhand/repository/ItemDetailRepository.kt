package id.finalproject.binar.secondhand.repository

import id.finalproject.binar.secondhand.service.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ItemDetailRepository(private  val apiService: ApiService) {
    suspend fun postOrderBuy(
        access_token: String,
        product_id: RequestBody,
        bid_price: RequestBody
    ) = apiService.postOrderBuyer(access_token, product_id, bid_price)
}