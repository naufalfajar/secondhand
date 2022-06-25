package id.finalproject.binar.secondhand.repository

import id.finalproject.binar.secondhand.service.ApiService

class SellerOrderRepository(private val apiService: ApiService) {
    suspend fun getOrderSeller(access_token: String) = apiService.getOrderSeller(access_token)

    suspend fun getOrderByIdSeller(orderId: Int, access_token: String) =
        apiService.getOrderByIdSeller(orderId, access_token)
}