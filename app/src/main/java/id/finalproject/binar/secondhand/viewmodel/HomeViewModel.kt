package id.finalproject.binar.secondhand.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.finalproject.binar.secondhand.repository.BannerRepository
import id.finalproject.binar.secondhand.repository.CategoryRepository
import id.finalproject.binar.secondhand.repository.ProductRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    productRepository: ProductRepository,
    categoryRepository: CategoryRepository,
    bannerRepository: BannerRepository
) : ViewModel() {

    val product = productRepository.getProduct().asLiveData()
    val category = categoryRepository.getCategory().asLiveData()
    val banner = bannerRepository.getBanner().asLiveData()
}