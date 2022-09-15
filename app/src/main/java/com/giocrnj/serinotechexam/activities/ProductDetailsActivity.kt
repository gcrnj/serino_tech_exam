package com.giocrnj.serinotechexam.activities

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Paint
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.giocrnj.serinotechexam.Constants
import com.giocrnj.serinotechexam.R
import com.giocrnj.serinotechexam.databinding.ActivityProductDetailsBinding
import com.giocrnj.serinotechexam.models.Product
import com.giocrnj.serinotechexam.view_models.ProductViewModel
import com.giocrnj.serinotechexam.views.ImagesAdapter


class ProductDetailsActivity : BaseActivity() {

    //view binding
    lateinit var binding: ActivityProductDetailsBinding

    //view model
    lateinit var productViewModel: ProductViewModel

    //horizontal linearlayout manager
    lateinit var linearLayoutManager: LinearLayoutManager

    //animator
    lateinit var defaultItemAnimator: DefaultItemAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBackButtonToolBar()

        //recyclerview
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        defaultItemAnimator = DefaultItemAnimator()

        //view model
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        //observe
        observeProduct()

        //get product data from intent
        //set it to view model
        productViewModel.product.value = intent.getParcelableExtra(Constants.PRODUCT_KEY)
    }

    private fun observeProduct() {
        productViewModel.product.observe(this) {
            setContents(it)
        }
    }

    private fun setContents(product: Product?) {

        if (product == null) {
            //Show alert dialog saying no product found
            alertNoProductFound()
            return
        }

        //load images with glide
        loadImageWithGlide(product.thumbnail, binding.imgProduct, true)

        //set images adapter
        binding.recyclerImages.adapter = ImagesAdapter(this, product.images)
        binding.recyclerImages.layoutManager = linearLayoutManager
        binding.recyclerImages.itemAnimator = defaultItemAnimator

        //prices
        val originalPrice = product.price
        val discountedPrice = originalPrice - ((product.discountPercentage / 100) * originalPrice)
        //texts
        binding.txtTitle.text = product.title
        binding.txtDescription.text = product.description
        binding.txtPrice.text =
            getString(
                R.string.with_peso_sign,
                formatNumbersWithComma(
                    discountedPrice
                )
            ) //add peso sign
        //check if there is a discount
        if (product.discountPercentage > 0.0) {
            binding.txtPriceDiscount.paintFlags =
                binding.txtPriceDiscount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            binding.txtPriceDiscount.text =
                getString(
                    R.string.with_peso_sign,
                    formatNumbersWithComma(product.price)
                ) //add peso sign
        } else {
            binding.txtPriceDiscount.text = null
        }
        binding.txtRating.text =
            getString(R.string.rating_with_denominator, product.rating.toString(), "5")
        binding.txtStock.text = product.stock.toString()
        binding.txtCategory.text = product.category
        binding.txtBrand.text = product.brand
    }

    private fun alertNoProductFound() {
        //build and show dialog
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.no_product_title))
            .setMessage(getString(R.string.no_product_message))
            .setPositiveButton(
                getString(R.string.ok)
            ) { dialog, _ ->
                //dismiss dialog to avoid crashing of the app
                dialog.dismiss()
                //finish activity
                finish()
            }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }
}