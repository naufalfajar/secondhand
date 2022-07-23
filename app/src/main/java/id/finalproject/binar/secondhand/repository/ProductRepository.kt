package id.finalproject.binar.secondhand.repository

import androidx.room.withTransaction
import id.finalproject.binar.secondhand.model.local.SecondHandDatabase
import id.finalproject.binar.secondhand.model.network.response.GetSellerInfoForBuyer
import id.finalproject.binar.secondhand.model.network.response.PostBuyerOrderResponse
import id.finalproject.binar.secondhand.service.SecondHandApi
import id.finalproject.binar.secondhand.util.networkBoundResource
import kotlinx.coroutines.delay
import okhttp3.RequestBody
import okhttp3.Response
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

    fun getProductbyId(productId: Int) = networkBoundResource(
        query = {
            productDao.getProductById(productId)
        },
        fetch = {
            delay(2000)
            api.getProductById(productId)
        },
        saveFetchResult = { product ->
            db.withTransaction {
                productDao.deleteProduct()
                productDao.insertProductDetail(product)
            }
        }
    )

    suspend fun postOrderBuy(
        access_token: String,
        product_id: RequestBody,
        bid_price: RequestBody
    ) : retrofit2.Response<PostBuyerOrderResponse> {
        return api.postOrderBuyer(access_token, product_id, bid_price)
    }

    suspend fun getSellerInfo(
        productId: Int
    ) : GetSellerInfoForBuyer{
        return api.getProductByIdFromBuyer(productId)
    }

}