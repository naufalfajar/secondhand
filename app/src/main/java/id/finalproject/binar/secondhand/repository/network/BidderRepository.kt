package id.finalproject.binar.secondhand.repository.network

import id.finalproject.binar.secondhand.service.ApiService

class BidderRepository(private val apiService: ApiService) {

    suspend fun getBidder(access_token: String) = apiService.getNotification(access_token)

    suspend fun getBidderById(notificationId: Int, access_token: String) =
        apiService.getNotificationById(notificationId, access_token)

}