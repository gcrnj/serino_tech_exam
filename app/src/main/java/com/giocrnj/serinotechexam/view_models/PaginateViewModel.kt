package com.giocrnj.serinotechexam.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.giocrnj.serinotechexam.models.Paginate
import com.giocrnj.serinotechexam.models.Product
import com.giocrnj.serinotechexam.views.ProductsAdapter

class PaginateViewModel : ViewModel() {

    val latestAdapter : MutableLiveData<ProductsAdapter> by lazy {
        MutableLiveData<ProductsAdapter>().also {
            //Todo - Get pagination from Sqlite
            //it.value = Paginate(10, emptyList(), 5, 0)
        }
    }

    val paginate: MutableLiveData<Paginate> by lazy {
        MutableLiveData<Paginate>().also {
            //Todo - Get pagination from Sqlite
            //it.value = Paginate(10, emptyList(), 5, 0)
        }
    }

    val isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().also {
            it.value = true
        }
    }


}