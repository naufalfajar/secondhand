package id.finalproject.binar.secondhand.repository

import androidx.room.withTransaction
import id.finalproject.binar.secondhand.model.local.SecondHandDatabase
import id.finalproject.binar.secondhand.service.SecondHandApi
import id.finalproject.binar.secondhand.util.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val api: SecondHandApi,
    private val db: SecondHandDatabase
) {
    private val historyDao = db.historyDao()

    fun getHistory(access_token: String) = networkBoundResource(
        query = {
            historyDao.getHistory()
        },
        fetch = {
            delay(2000)
            api.getHistory(access_token)
        },
        saveFetchResult = { history ->
            db.withTransaction {
                historyDao.insertHistory(history)
                historyDao.deleteHistory()
            }

        }
    )
}