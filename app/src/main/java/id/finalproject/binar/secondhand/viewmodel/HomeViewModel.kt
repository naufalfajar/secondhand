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

    val getProduct = productRepository.getProduct().asLiveData()
    val getCategory = categoryRepository.getCategory().asLiveData()
    val getBanner = bannerRepository.getBanner().asLiveData()

}