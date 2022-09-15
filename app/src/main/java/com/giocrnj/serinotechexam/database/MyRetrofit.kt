package com.giocrnj.serinotechexam.database

import android.app.Activity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyRetrofit(activity: Activity, baseUrl : String) {

    val retrofit = Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}