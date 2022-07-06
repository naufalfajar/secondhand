package id.finalproject.binar.secondhand.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.finalproject.binar.secondhand.repository.CategoryRepository
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    repository: CategoryRepository
) : ViewModel() {
    val category = repository.getCategory().asLiveData()
}