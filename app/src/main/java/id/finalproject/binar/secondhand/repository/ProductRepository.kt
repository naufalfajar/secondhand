package id.finalproject.binar.secondhand.repository

import androidx.room.withTransaction
import id.finalproject.binar.secondhand.model.local.SecondHandDatabase
import id.finalproject.binar.secondhand.service.ApiService
import id.finalproject.binar.secondhand.service.SecondHandApi
import id.finalproject.binar.secondhand.util.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val api: SecondHandApi,
    private val db: SecondHandDatabase
) {
    private val productDao = db.productDao()

    fun getProduct() = networkBoundResource(
        query = {
            productDao.getProduct()
        },
        fetch = {
            delay(2000)
            api.getProduct()
        },
        saveFetchResult = { product ->
            db.withTransaction {
                productDao.deleteProduct()
                productDao.insertProduct(product)
            }

        }
    )
}