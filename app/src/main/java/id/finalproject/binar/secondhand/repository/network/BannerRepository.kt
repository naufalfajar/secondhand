package id.finalproject.binar.secondhand.repository.network

import id.finalproject.binar.secondhand.service.ApiService

class BannerRepository(private val apiService: ApiService) {
    suspend fun getBanner() = apiService.getBanner()
    suspend fun getBannerById(bannerId: Int) = apiService.getBannerById(bannerId)
}