package id.finalproject.binar.secondhand.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import id.finalproject.binar.secondhand.helper.SharedPreferences
import id.finalproject.binar.secondhand.model.network.Resource
import id.finalproject.binar.secondhand.model.network.response.ApiError
import id.finalproject.binar.secondhand.repository.CategoryRepository
import id.finalproject.binar.secondhand.repository.SellerAddProductRepository
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class SellerProductViewModel @Inject constructor(
    private val repository: SellerAddProductRepository): ViewModel() {

    fun postProduct(
        access_token: String,
        name: RequestBody,
        description: RequestBody,
        base_price: RequestBody,
        category_ids: RequestBody,
        location: RequestBody,
        image: MultipartBody.Part
    ) = liveData(Dispatchers.IO){

        emit(Resource.loading(null))
        try {
            val response = repository.postProduct(access_token, name, description, base_price, category_ids,location, image)
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

    fun getUser(
        access_token: String
    ) = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repository.getUser(access_token)))
        } catch (e: Exception){
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred"))
        }
    }

    fun getCategory() = repository.getAllCategory().asLiveData()

}