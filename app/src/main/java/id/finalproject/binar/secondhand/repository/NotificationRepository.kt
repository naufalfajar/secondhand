package id.finalproject.binar.secondhand.repository

import androidx.room.withTransaction
import id.finalproject.binar.secondhand.model.local.SecondHandDatabase
import id.finalproject.binar.secondhand.service.SecondHandApi
import id.finalproject.binar.secondhand.util.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    private val api: SecondHandApi,
    private val db: SecondHandDatabase
) {
    private val notificationDao = db.notificationDao()

    fun getNotification(access_token: String) = networkBoundResource(
        query = {
            notificationDao.getNotification()
        },
        fetch = {
            delay(2000)
            api.getNotification(access_token)
        },
        saveFetchResult = { notification ->
            db.withTransaction {
                notificationDao.deleteNotification()
                notificationDao.insertNotification(notification)
            }

        }
    )
}