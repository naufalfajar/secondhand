package id.finalproject.binar.secondhand.fragment.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.finalproject.binar.secondhand.model.network.Resource
import id.finalproject.binar.secondhand.repository.UserRepo
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class UpdateViewModel(private val userRepo: UserRepo): ViewModel() {

    fun putUpdateUser(
        access_token: String,
        full_name: RequestBody,
        email: RequestBody,
//        password: RequestBody,
        phone_number: RequestBody,
        address: RequestBody,
        image: MultipartBody.Part,
        city: RequestBody
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(userRepo.putUser(
                access_token, full_name, email,
//                password,
                phone_number, address, image, city)))
        } catch (e: Exception) {
            emit(
                Resource.error(data = null, message = e.message ?: "Error Occurred!")
            )
        }
    }

    fun getUser(access_token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(
                Resource.success(userRepo.getUser(access_token)))
        } catch (e: Exception) {
            emit(
                (e as? HttpException)!!.response()?.errorBody()?.string()?.let {
                    Resource.error(
                        data = null,
                        message = it
                    )
                }
            )
        }
    }
}