package com.giocrnj.serinotechexam.views

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Paint
import android.util.Pair
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.giocrnj.serinotechexam.Constants
import com.giocrnj.serinotechexam.R
import com.giocrnj.serinotechexam.activities.BaseActivity
import com.giocrnj.serinotechexam.activities.FullScreenImageActivity
import com.giocrnj.serinotechexam.activities.ProductDetailsActivity
import com.giocrnj.serinotechexam.models.Product
import java.util.*

class ProductsAdapter(private val activity: Activity, private val products: MutableList<Product>) :
    RecyclerView.Adapter<ProductsAdapter.ProductsHolder>() {

    class ProductsHolder(productView: View) : RecyclerView.ViewHolder(productView) {
        val cardProduct: CardView = productView.findViewById(R.id.cardProduct)
        val imgProduct: ImageView = productView.findViewById(R.id.imgProduct)
        val txtTitle: AppCompatTextView = productView.findViewById(R.id.txtTitle)
        val txtDescription: AppCompatTextView = productView.findViewById(R.id.txtDescription)
        val txtPrice: AppCompatTextView = productView.findViewById(R.id.txtPrice)
        val txtPriceDiscount: AppCompatTextView = productView.findViewById(R.id.txtPriceDiscount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsHolder {
        //Return the view
        return ProductsHolder(
            activity.layoutInflater.inflate(
                R.layout.layout_product_view,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductsHolder, position: Int) {
        //...Set contents of the view

        //Get current product
        val product = products[position]

        //Load image with glide
        (activity as BaseActivity).loadImageWithGlide(product.thumbnail, holder.imgProduct, true)

        //Set contents of the view
        val originalPrice = product.price
        val discountedPrice = originalPrice - ((product.discountPercentage / 100) * originalPrice)

        holder.txtTitle.text = product.title
        holder.txtDescription.text = product.description
        holder.txtPrice.text =
            activity.getString(
                R.string.with_peso_sign,
                activity.formatNumbersWithComma(
                    discountedPrice
                )
            ) //add peso sign
        //check if there is a discount
        if (product.discountPercentage > 0.0) {
            holder.txtPriceDiscount.paintFlags =
                holder.txtPriceDiscount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.txtPriceDiscount.text =
                activity.getString(
                    R.string.with_peso_sign,
                    activity.formatNumbersWithComma(product.price)
                ) //add peso sign
        } else {
            holder.txtPriceDiscount.text = null
        }

        //Set click listener
        holder.cardProduct.setOnClickListener {
            val productIntent = Intent(activity, ProductDetailsActivity::class.java)
            productIntent.putExtra(Constants.PRODUCT_KEY, product)
            val options =
                ActivityOptions.makeSceneTransitionAnimation(
                    activity,
                    Pair.create(holder.imgProduct, holder.imgProduct.transitionName),
                    Pair.create(holder.txtTitle, holder.txtTitle.transitionName)
                )
            activity.startActivity(productIntent, options.toBundle())
        }

        holder.imgProduct.setOnClickListener {
            val fullImageIntent = Intent(activity, FullScreenImageActivity::class.java)
            fullImageIntent.putExtra(Constants.IMAGE_KEY, product.thumbnail)
            activity.startActivity(fullImageIntent)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    //add item
    fun addProducts(newProducts: List<Product>) {
        products.addAll(newProducts)
    }

}