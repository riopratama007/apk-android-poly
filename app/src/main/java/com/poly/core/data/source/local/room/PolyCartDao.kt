package com.poly.core.data.source.local.room

import androidx.room.*
import com.poly.core.data.source.local.entitiy.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PolyCartDao {
    @Query("SELECT * FROM tb_cart ORDER BY id ASC")
    fun loadMyCart(): Flow<List<CartEntity>>

    @Query("SELECT * FROM tb_cart WHERE is_checked = :isChecked")
    fun loadSelectedCart(isChecked: Boolean): Flow<List<CartEntity>>

    @Update
    fun updateMyCart(cartEntity: CartEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cartEntity: CartEntity): Long

    @Query("DELETE FROM tb_cart WHERE id = :id")
    suspend fun deleteCartById(id: Int): Int
}