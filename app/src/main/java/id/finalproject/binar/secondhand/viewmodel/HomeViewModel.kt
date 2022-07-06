package id.finalproject.binar.secondhand.viewmodel

import androidx.lifecycle.ViewModel
import id.finalproject.binar.secondhand.repository.network.BannerRepository

class HomeViewModel(
    private val bannerRepository: BannerRepository,
) : ViewModel() {

//    fun getBanner() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(null))
//        try {
//            emit(Resource.success(bannerRepository.getBanner()))
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
}