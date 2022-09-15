package com.giocrnj.serinotechexam.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.giocrnj.serinotechexam.models.Product

class ProductViewModel : ViewModel() {

    val product: MutableLiveData<Product> by lazy {
        MutableLiveData<Product>()
    }

}