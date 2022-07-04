package id.finalproject.binar.secondhand.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.finalproject.binar.secondhand.model.network.Resource
import id.finalproject.binar.secondhand.repository.SellerAddProductRepository
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import okhttp3.RequestBody

class SellerProductViewModel(private val repository: SellerAddProductRepository): ViewModel() {

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
            emit(Resource.success(repository.postProduct(access_token, name, description, base_price, category_ids,location,
                image
            )))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun getProduct(
        access_token: String
    ) = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repository.getProduct(access_token)))
        } catch (e: Exception){
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred"))
        }
    }

    fun postRegister(
        full_name: RequestBody,
        email: RequestBody,
        password: RequestBody,
        phone_number: RequestBody,
        address: RequestBody,
        image: MultipartBody.Part,
        city: RequestBody
    ) = liveData(Dispatchers.IO){

        emit(Resource.loading(null))
        try {
            emit(Resource.success(repository.register(
                full_name,
                email,
                password,
                phone_number,
                address,
                image,
                city
            )))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }
}