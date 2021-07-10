package com.naufalfachrian.itunessearch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    val query = MutableLiveData<String>()

}