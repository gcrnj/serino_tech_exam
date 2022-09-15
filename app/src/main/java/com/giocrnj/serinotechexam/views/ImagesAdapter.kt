package com.giocrnj.serinotechexam.views

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.recyclerview.widget.RecyclerView
import com.giocrnj.serinotechexam.Constants
import com.giocrnj.serinotechexam.R
import com.giocrnj.serinotechexam.activities.BaseActivity
import com.giocrnj.serinotechexam.activities.FullScreenImageActivity

class ImagesAdapter(private val activity: Activity, private val urls: List<String>) :
    RecyclerView.Adapter<ImagesAdapter.ImagesHolder>() {

    class ImagesHolder(productView: View) : RecyclerView.ViewHolder(productView) {
        val imgProduct: ImageFilterView = productView.findViewById(R.id.imgProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesHolder {
        //Return the view
        return ImagesHolder(
            activity.layoutInflater.inflate(
                R.layout.layout_product_image,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImagesHolder, position: Int) {
        //...Set contents of the view

        //Get current product
        val url = urls[position]

        //Load image with glide
        (activity as BaseActivity).loadImageWithGlide(url, holder.imgProduct, true)

        //set click listener
        holder.imgProduct.setOnClickListener {
            val fullImageIntent = Intent(activity, FullScreenImageActivity::class.java)
            fullImageIntent.putExtra(Constants.IMAGE_KEY, url)
            activity.startActivity(fullImageIntent)
        }
    }

    override fun getItemCount(): Int {
        return urls.size
    }

}