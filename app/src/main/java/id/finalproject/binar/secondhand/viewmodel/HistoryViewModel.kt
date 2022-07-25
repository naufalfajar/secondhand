package id.finalproject.binar.secondhand.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.finalproject.binar.secondhand.model.network.Resource
import id.finalproject.binar.secondhand.repository.HistoryRepository
import id.finalproject.binar.secondhand.repository.network.SellerOrderRepository
import kotlinx.coroutines.Dispatchers

class HistoryViewModel(private val repository: HistoryRepository) : ViewModel() {

    fun getHistory(accessToken: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repository.getHistory(accessToken)))

        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }

    }

    fun getHistoryById(orderId: Int, accessToken: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(
                Resource.success(
                    repository.getHistoryById(
                        orderId,
                        accessToken
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

}