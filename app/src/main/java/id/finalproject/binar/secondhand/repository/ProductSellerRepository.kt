package id.finalproject.binar.secondhand.repository

import androidx.room.withTransaction
import id.finalproject.binar.secondhand.model.local.SecondHandDatabase
import id.finalproject.binar.secondhand.service.SecondHandApi
import id.finalproject.binar.secondhand.util.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

class ProductSellerRepository @Inject constructor(
    private val api: SecondHandApi,
    private val db: SecondHandDatabase
) {
    private val productSellerDao = db.productSellerDao()

    fun getProduct(access_token: String) = networkBoundResource(
        query = {
            productSellerDao.getProduct()
        },
        fetch = {
            delay(2000)
            api.getProductSeller(access_token)
        },
        saveFetchResult = { product ->
            db.withTransaction {
                productSellerDao.deleteProduct()
                productSellerDao.insertProduct(product)
            }

        }
    )
}