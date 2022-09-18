package com.poly.core.domain.model

data class Cart(
    val id: Int,
    val image: String,
    val title: String,
    val description: String,
    val price: Int,
    val qty: Int,
    val totalPrice: Int,
    val isChecked: Boolean = false
)
