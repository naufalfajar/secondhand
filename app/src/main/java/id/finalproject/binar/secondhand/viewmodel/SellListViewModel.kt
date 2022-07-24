package id.finalproject.binar.secondhand.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.finalproject.binar.secondhand.helper.SharedPreferences
import id.finalproject.binar.secondhand.repository.HistoryRepository
import id.finalproject.binar.secondhand.repository.ProductSellerRepository
import id.finalproject.binar.secondhand.repository.UserRepo
import javax.inject.Inject

@HiltViewModel
class SellListViewModel @Inject constructor(
    productSellerRepository: ProductSellerRepository,
    historyRepository: HistoryRepository,
    userRepo: UserRepo,
    sharedPref: SharedPreferences
) : ViewModel() {

    val accessToken = sharedPref.getToken()!!
    val isLogin = sharedPref.getLogin()

    val getProduct = productSellerRepository.getProduct(accessToken).asLiveData()
    val getHistory = historyRepository.getHistory(accessToken).asLiveData()
    val getUser = userRepo.getUser(accessToken).asLiveData()

}