package id.finalproject.binar.secondhand.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.finalproject.binar.secondhand.repository.ProductRepository
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    repository: ProductRepository
) : ViewModel() {
    val product = repository.getProduct().asLiveData()
}