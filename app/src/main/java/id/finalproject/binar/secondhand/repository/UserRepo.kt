package id.finalproject.binar.secondhand.repository

import androidx.room.withTransaction
import id.finalproject.binar.secondhand.model.local.SecondHandDatabase
import id.finalproject.binar.secondhand.service.SecondHandApi
import id.finalproject.binar.secondhand.util.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

class UserRepo @Inject constructor(
    private val api: SecondHandApi,
    private val db: SecondHandDatabase
) {
    private val userDao = db.userDao()

    fun getUser(access_token: String) = networkBoundResource(
        query = {
            userDao.getUser()
        },
        fetch = {
            delay(2000)
            api.getUserData(access_token)
        },
        saveFetchResult = { user ->
            db.withTransaction {
                userDao.insertDao(user)
                userDao.deleteUser()
            }

        }
    )
}