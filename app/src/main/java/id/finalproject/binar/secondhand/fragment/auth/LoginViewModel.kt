package id.finalproject.binar.secondhand.fragment.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.finalproject.binar.secondhand.model.network.Resource
import id.finalproject.binar.secondhand.model.network.request.LoginRequest
import id.finalproject.binar.secondhand.repository.UserRepo
import kotlinx.coroutines.Dispatchers
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException

class LoginViewModel(private val userRepo: UserRepo): ViewModel() {
    fun postLoginUser(req: LoginRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(
                Resource.success(userRepo.postLogin(req)))
        } catch (e: Exception) {
            emit(
                (e as? HttpException)!!.response()?.errorBody()?.string()?.let {
                    Resource.error(
                        data = null,
                        message = it
                    )
                }
            )
        }
    }
}