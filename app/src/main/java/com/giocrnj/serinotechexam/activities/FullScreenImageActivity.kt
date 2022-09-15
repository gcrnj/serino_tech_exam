package com.giocrnj.serinotechexam.activities

import android.app.AlertDialog
import android.os.Bundle
import com.giocrnj.serinotechexam.Constants
import com.giocrnj.serinotechexam.R
import com.giocrnj.serinotechexam.databinding.ActivityFullScreenImageBinding

class FullScreenImageActivity : BaseActivity() {
    lateinit var binding: ActivityFullScreenImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullScreenImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBackButtonToolBar()

        val imageUrl = intent.getStringExtra(Constants.IMAGE_KEY)
        if (imageUrl == null) {
            alertNoImageFound()
            return
        } else {
            loadImageWithGlide(imageUrl, binding.imgProduct, false)
        }
    }


    private fun alertNoImageFound() {
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