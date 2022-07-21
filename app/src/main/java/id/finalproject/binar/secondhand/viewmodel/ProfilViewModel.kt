package id.finalproject.binar.secondhand.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.finalproject.binar.secondhand.helper.SharedPreferences
import javax.inject.Inject

@HiltViewModel
class ProfilViewModel @Inject constructor(
    sharedPref: SharedPreferences
) : ViewModel()