package id.finalproject.binar.secondhand.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import id.finalproject.binar.secondhand.helper.SharedPreferences
import id.finalproject.binar.secondhand.model.network.Resource
import id.finalproject.binar.secondhand.repository.network.SellerOrderRepository
import kotlinx.coroutines.Dispatchers

class SellerOrderViewModel(private val repository: SellerOrderRepository) : ViewModel() {


    fun getOrderSeller(accessToken: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repository.getOrderSeller(accessToken)))

        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }

    }

    fun getOrderByIdSeller(orderId: Int, accessToken: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(
                Resource.success(
                    repository.getOrderByIdSeller(
                        orderId,
                        accessToken
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


//    fun patchNotifcationById(notificationId: Int) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(null))
//        try {
//            emit(
//                Resource.success(
//                    repository.patchNotificationById(
//                        notificationId,
//                        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImpvaG5kb2VAbWFpbC5jb20iLCJpYXQiOjE2NTQ5MjcxODZ9.fghFryd8OPEHztZlrN50PtZj0EC7NWFVj2iPPN9xi1M"
//                    )
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
}