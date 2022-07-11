//package id.finalproject.binar.secondhand.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.liveData
//import id.finalproject.binar.secondhand.model.network.Resource
//import kotlinx.coroutines.Dispatchers
//
//class BuyerViewModel (private val networkRepository: id.finalproject.binar.secondhand.repository.network.BuyerProductRepository) : ViewModel() {
//
//    fun getProductByIdBuyer(productId: Int) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(null))
//        try {
//            emit(
//                Resource.success(
//                    networkRepository.getProductByIdBuyer(productId)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//}