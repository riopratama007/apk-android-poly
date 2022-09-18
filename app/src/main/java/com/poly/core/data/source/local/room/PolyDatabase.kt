package com.poly.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.poly.core.data.source.local.entitiy.CartEntity

@Database(
    entities = [CartEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PolyDatabase : RoomDatabase() {
    abstract fun kitaSehatCartDao(): PolyCartDao
}