package id.finalproject.binar.secondhand.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.finalproject.binar.secondhand.model.network.Resource
import id.finalproject.binar.secondhand.repository.network.BuyerProductRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val repository: BuyerProductRepository) : ViewModel() {

    fun getProductBuyer() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repository.getProductBuyer()))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun getProductByIdBuyer(productId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(
                Resource.success(
                    repository.getProductByIdBuyer(productId)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }
}