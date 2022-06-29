package id.finalproject.binar.secondhand.repository.network

import id.finalproject.binar.secondhand.service.ApiService

class BuyerProductRepository(private val apiService: ApiService) {

    suspend fun getProductBuyer() = apiService.getProductBuyer()
    suspend fun getProductByIdBuyer(productId: Int) = apiService.getProductByIdBuyer(productId)

}