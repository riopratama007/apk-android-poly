package com.poly.core.data.source.local

import com.poly.core.data.source.local.entitiy.CartEntity
import com.poly.core.data.source.local.room.PolyCartDao

class LocalDataSource(
    private val kitaSehatCartDao: PolyCartDao
) {
    fun loadMyCart() = kitaSehatCartDao.loadMyCart()
    fun loadSelectedCart(isChecked: Boolean) = kitaSehatCartDao.loadSelectedCart(isChecked)
    fun updateMyCart(cartEntity: CartEntity) = kitaSehatCartDao.updateMyCart(cartEntity)
    suspend fun insertCart(cartEntity: CartEntity) = kitaSehatCartDao.insertCart(cartEntity)
    suspend fun deleteCartById(id: Int) = kitaSehatCartDao.deleteCartById(id)
}