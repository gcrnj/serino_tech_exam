package com.giocrnj.serinotechexam.api

import android.app.Activity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface OwnListener<T> : Callback<T> {

    fun onSuccess(it: T) // 200
    fun onFailed() // went through but failed / not 200
    fun onException(t: Throwable) //Something went wrong

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                onSuccess(body)
            }
        } else {
            onFailed()
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
    }
}