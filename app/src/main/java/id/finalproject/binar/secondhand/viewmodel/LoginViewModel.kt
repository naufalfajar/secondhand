package id.finalproject.binar.secondhand.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.finalproject.binar.secondhand.helper.SharedPreferences
import id.finalproject.binar.secondhand.model.network.Resource
import id.finalproject.binar.secondhand.model.network.request.LoginRequest
import id.finalproject.binar.secondhand.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPref: SharedPreferences
) : ViewModel() {

    fun postLoginUser(req: LoginRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(
                Resource.success(userRepository.postLogin(req))
            )
        } catch (e: Exception) {
            emit(
                Resource.error(
                    data = null,
                    message = e.message ?: "Gagal dimuat!"
                )
            )
        }
    }

    fun saveDataLogin(access_token: String, email: String) {
        viewModelScope.launch {
            sharedPref.saveEmail(email)
            sharedPref.saveToken(access_token)
            sharedPref.saveStatusLogin()
        }

    }
}