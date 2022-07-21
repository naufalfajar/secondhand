package id.finalproject.binar.secondhand.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.finalproject.binar.secondhand.model.network.Resource
import id.finalproject.binar.secondhand.repository.ItemDetailRepository
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import okhttp3.RequestBody

class BuyerViewModel (private val repository: ItemDetailRepository) : ViewModel() {

    fun postOrderBuy(
        access_token: String,
        product_id: RequestBody,
        bid_price: RequestBody
    ) = liveData(Dispatchers.IO){

        emit(Resource.loading(null))
        try {
            emit(Resource.success(repository.postOrderBuy(access_token, product_id, bid_price)
            ))
        } catch (e: Exception) {
            emit(Resource.error(
                data = null,
                message = e.message ?: "Error Occurred!"))
        }
    }
}