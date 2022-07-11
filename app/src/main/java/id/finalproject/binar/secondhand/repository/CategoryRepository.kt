package id.finalproject.binar.secondhand.repository

import androidx.room.withTransaction
import id.finalproject.binar.secondhand.model.local.SecondHandDatabase
import id.finalproject.binar.secondhand.service.SecondHandApi
import id.finalproject.binar.secondhand.util.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val api: SecondHandApi,
    private val db: SecondHandDatabase
) {
    private val categoryDao = db.categoryDao()

    fun getCategory() = networkBoundResource(
        query = {
            categoryDao.getCategory()
        },
        fetch = {
            delay(2000)
            api.getCategory()
        },
        saveFetchResult = { category ->
            db.withTransaction {
                categoryDao.deleteCategory()
                categoryDao.insertCategory(category)
            }

        }
    )
}