package com.giocrnj.serinotechexam.activities

import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.giocrnj.serinotechexam.R
import com.giocrnj.serinotechexam.api.ProductsApi
import com.giocrnj.serinotechexam.database.MyRetrofit
import java.text.DecimalFormat
import java.text.NumberFormat


open abstract class BaseActivity : AppCompatActivity() {

    lateinit var myProducts: ProductsApi
    lateinit var serverUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //api
        serverUrl = getString(R.string.server_url)
        myProducts = MyRetrofit(this, serverUrl).retrofit.create(ProductsApi::class.java)

    }

    fun setBackButtonToolBar() {
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    fun loadImageWithGlide(url: String, imageView: ImageView, centerCrop: Boolean) {
        val placeholderDrawable: Drawable? =
            ContextCompat.getDrawable(this, R.drawable.android)
        //Load image to an imageview
        Log.d("Glide_URL", url)
        if (centerCrop) {
            Glide
                .with(applicationContext)
                .load(url)
                .centerCrop()
                .placeholder(placeholderDrawable)
                .into(imageView)
        } else {
            Glide
                .with(applicationContext)
                .load(url)
                .placeholder(placeholderDrawable)
                .into(imageView)
        }
    }

    fun startSkeletonLoading(imageViews: List<ImageView>) {
        var rocketAnimation: AnimationDrawable
        imageViews.all {
            it.setBackgroundResource(R.drawable.skeleton_drawable)
            rocketAnimation = (it.background as AnimationDrawable)
            rocketAnimation.setEnterFadeDuration(500)
            rocketAnimation.setExitFadeDuration(500)

            true
        }
    }

    fun formatNumbersWithComma(value: Int): String {
        val formatter: NumberFormat = DecimalFormat("###,###,###.##")
        return formatter.format(value)
    }

    fun formatNumbersWithComma(value: Double): String {
        val formatter: NumberFormat = DecimalFormat("###,###,###.##")
        return formatter.format(value)
    }
}