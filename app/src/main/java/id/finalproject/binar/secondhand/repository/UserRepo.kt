package id.finalproject.binar.secondhand.repository

import id.finalproject.binar.secondhand.model.network.request.LoginRequest
import id.finalproject.binar.secondhand.service.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UserRepo(private val apiService: ApiService) {
    suspend fun postRegis(
        full_name: RequestBody,
        email: RequestBody,
        password: RequestBody,
        phone_number: RequestBody,
        address: RequestBody,
        image: MultipartBody.Part,
        city: RequestBody
    ) = apiService.postRegis(
        full_name,
        email,
        password,
        phone_number,
        address,
        image,
        city)

    suspend fun postLogin(req: LoginRequest) = apiService.postLogin(req)
}