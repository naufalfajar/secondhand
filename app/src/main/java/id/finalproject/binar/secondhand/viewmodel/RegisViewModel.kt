package id.finalproject.binar.secondhand.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.finalproject.binar.secondhand.model.network.Resource
import id.finalproject.binar.secondhand.repository.UserRepo
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RegisViewModel(private val userRepo: UserRepo): ViewModel() {
    fun postRegisUser(
        full_name: RequestBody,
        email: RequestBody,
        password: RequestBody,
        phone_number: RequestBody,
        address: RequestBody,
        image: MultipartBody.Part,
        city: RequestBody
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(userRepo.postRegis(
                full_name, email, password, phone_number,
                address, image, city)))
        } catch (e: Exception) {
            emit(Resource.error(
                data = null,
                message = e.message ?: "Gagal dimuat!"))
        }
    }
}