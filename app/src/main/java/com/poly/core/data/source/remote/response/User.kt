package com.poly.core.data.source.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


object User {
    @Parcelize
    data class Data(
        val alamat: String,
        val email: String,
        val id: String,
        val imageUrl: String,
        val jenisKelamin: String,
        val nama: String,
        val no: String,
        val password: String,
        val pendidikan: String,
        val profesi: String
    ): Parcelable
}