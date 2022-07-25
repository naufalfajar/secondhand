package id.finalproject.binar.secondhand.repository

import id.finalproject.binar.secondhand.service.ApiService


class HistoryRepository(private val apiService: ApiService) {
    suspend fun getHistory(access_token: String) = apiService.getHistory(access_token)

    suspend fun getHistoryById(orderId: Int, access_token: String) =
        apiService.getHistoryById(orderId, access_token)
}