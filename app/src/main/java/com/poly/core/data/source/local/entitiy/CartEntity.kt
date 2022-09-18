package com.poly.core.data.source.local.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_cart")
data class CartEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "qty") val qty: Int,
    @ColumnInfo(name = "totalPrice") val totalPrice: Int,
    @ColumnInfo(name = "is_checked") val isChecked: Boolean = false
)
