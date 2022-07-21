package id.finalproject.binar.secondhand.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import id.finalproject.binar.secondhand.model.network.Resource
import id.finalproject.binar.secondhand.model.network.response.ApiError
import id.finalproject.binar.secondhand.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class BuyerViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {

    fun getProductDetail(productId: Int) = productRepository.getProductbyId(productId).asLiveData()

    fun postOrderBuy(
        access_token: String,
        product_id: RequestBody,
        bid_price: RequestBody
    ) = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            val response = productRepository.postOrderBuy(access_token, product_id, bid_price)
            if(response.isSuccessful){
                emit(Resource.success(response.body()))
            }else{
                val gson = Gson()
                val errorMsg = response.errorBody()?.string()
                val data = gson.fromJson(errorMsg, ApiError::class.java)
                response.errorBody()?.close()
                emit(Resource.error(null, data.message))
            }
        } catch (e: Exception) {
            emit(Resource.error(
                data = null,
                message = e.message ?: "Error Occurred!"))
        }
    }

}