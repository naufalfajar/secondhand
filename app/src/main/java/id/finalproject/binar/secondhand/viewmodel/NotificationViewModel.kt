package id.finalproject.binar.secondhand.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.finalproject.binar.secondhand.helper.SharedPreferences
import id.finalproject.binar.secondhand.repository.NotificationRepository
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    repository: NotificationRepository,
    sharedPref: SharedPreferences
) : ViewModel() {

    val accessToken = sharedPref.getToken()!!
    val isLogin = sharedPref.getLogin()

    val notification = repository.getNotification(accessToken)
        .asLiveData()

}