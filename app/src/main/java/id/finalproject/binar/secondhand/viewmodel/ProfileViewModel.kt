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
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPref: SharedPreferences
) : ViewModel() {

    fun getUser(access_token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(
                Resource.success(userRepository.getUser(access_token))
            )
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

    val login = sharedPref.getLogin()
    val token = sharedPref.getToken()

    fun deleteSesi() {
        viewModelScope.launch {
            sharedPref.sessionDelete()
        }
    }
}