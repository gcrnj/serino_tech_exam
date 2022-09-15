package com.giocrnj.serinotechexam.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import com.giocrnj.serinotechexam.api.OwnListener
import com.giocrnj.serinotechexam.databinding.ActivityProductsBinding
import com.giocrnj.serinotechexam.models.Paginate
import com.giocrnj.serinotechexam.models.Product
import com.giocrnj.serinotechexam.view_models.PaginateViewModel
import com.giocrnj.serinotechexam.views.ProductsAdapter
import retrofit2.Call

class ProductsActivity : BaseActivity() {

    //view binding
    lateinit var binding: ActivityProductsBinding

    //view model
    lateinit var paginateViewModel: PaginateViewModel

    //recyclerview
    lateinit var defaultAnimator: DefaultItemAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //recyclerview
        defaultAnimator = DefaultItemAnimator()

        //view model
        paginateViewModel = ViewModelProvider(this)[PaginateViewModel::class.java]
        //observe
        initiateObservers()
        //get products from the server
        getProducts()
        //load more when at the bottom of screen
        binding.scrollProducts.setOnScrollChangeListener(onProductsScrollListener)

    }

    private fun initiateObservers() {
        paginateViewModel.paginate.observe(this) {
            //Get last adapter
            var lastAdapter = paginateViewModel.latestAdapter.value
            if (lastAdapter == null) {
                //Create new adapter
                lastAdapter = ProductsAdapter(this, (it.products as MutableList<Product>))
            } else {
                //Append to the last adapter
                lastAdapter.addProducts(it.products)
            }

            //set the adapter to live data
            paginateViewModel.latestAdapter.value = lastAdapter

            //set loading to avoid loading more
            paginateViewModel.isLoading.value = false

            //set check if maximum amount is met

            if (binding.recyclerProducts.adapter?.itemCount == it.total) {
                //Loading, HIDE SKELETON
                binding.relativeSkeletonLoading.visibility = View.GONE
            } else {
                //Loading, SHOW SKELETON
                binding.relativeSkeletonLoading.visibility = View.VISIBLE
                //Skeleton Loading
                startSkeletonLoading(
                    listOf(
                        binding.skeletonImgProduct,
                        binding.skeletonImgTitle,
                        binding.skeletonImgDescription,
                        binding.skeletonImgPrice
                    )
                )
            }
        }

        //Observe the adapter of recyclerview
        paginateViewModel.latestAdapter.observe(this) {

            //get recycler view's adapter
            val recyclerAdapter = binding.recyclerProducts.adapter
            if (recyclerAdapter == null) {
                binding.recyclerProducts.adapter = it
            } else {
                binding.recyclerProducts.adapter?.notifyItemChanged(recyclerAdapter.itemCount - 1)
            }

            //finally...set the layout manager and itemanimator
            binding.recyclerProducts.itemAnimator = defaultAnimator
        }

        paginateViewModel.isLoading.observe(this) {
        }
    }

    private fun getProducts() {

        //Get the response
        //If pagination is null, means it is the first time
        //Else, pass skip and limit to url
        val paginate = paginateViewModel.paginate.value
        val adapter = paginateViewModel.latestAdapter.value
        Log.d("Pagination", "$paginate")

        val theResponse: Call<Paginate>? = if (paginate == null || adapter == null) {
            myProducts.get(
                //by default, skip is 0
                //by default, limit is 10
                0, 10
            )
        } else {
            myProducts.get(
                adapter.itemCount, //skip the item retrieved
                paginate.limit //set limit to previous limit
            )
        }

        theResponse?.enqueue(object : OwnListener<Paginate> {
            override fun onSuccess(it: Paginate) {
                paginateViewModel.paginate.value = it
            }

            override fun onFailed() {
            }

            override fun onException(t: Throwable) {
            }
        })
    }

    //listen to scrollview
    private val onProductsScrollListener =
        NestedScrollView.OnScrollChangeListener { view, _, _, _, _ ->
            val isLoading = paginateViewModel.isLoading.value!!
            if (!view.canScrollVertically(1) && !isLoading) {
                paginateViewModel.isLoading.value = true
                //at the bottom. Then load more data
                //Not loading. Then load more data
                getProducts()
            }
        }
}