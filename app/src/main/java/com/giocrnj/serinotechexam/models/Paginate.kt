package com.giocrnj.serinotechexam.models

data class Paginate(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)