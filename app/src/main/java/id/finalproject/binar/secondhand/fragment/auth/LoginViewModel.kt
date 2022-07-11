package id.finalproject.binar.secondhand.fragment.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.finalproject.binar.secondhand.model.network.Resource
import id.finalproject.binar.secondhand.model.network.request.LoginRequest
import id.finalproject.binar.secondhand.repository.UserRepo
import kotlinx.coroutines.Dispatchers

class LoginViewModel(private val userRepo: UserRepo): ViewModel() {
    fun postLoginUser(req: LoginRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(
                Resource.success(userRepo.postLogin(req)))
        } catch (e: Exception) {
            emit(
                Resource.error(
                data = null,
                message = e.message ?: "Gagal dimuat!"))
        }
    }
}