package com.giocrnj.serinotechexam.api

import com.giocrnj.serinotechexam.models.Paginate
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ProductsApi {

    @GET("/products")
    fun get(): Call<Paginate>?

    @GET("/products")
    fun get(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Call<Paginate>?

}