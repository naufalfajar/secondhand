package id.finalproject.binar.secondhand.repository.network

import id.finalproject.binar.secondhand.service.ApiService

class NotificationRepository(private val apiService: ApiService) {

    suspend fun getNotification(access_token: String) = apiService.getNotification(access_token)

    suspend fun getNotificationById(notificationId: Int, access_token: String) =
        apiService.getNotificationById(notificationId, access_token)

    suspend fun patchNotificationById(notificationId: Int, access_token: String) =
        apiService.patchNotificationById(notificationId, access_token)
}