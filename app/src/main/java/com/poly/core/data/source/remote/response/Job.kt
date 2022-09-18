package com.poly.core.data.source.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

object Job {
    data class Request(
        val file: String,
        val judul: String,
        val deskripsi: String,
        val alamat: String,
        val user_id: String,
    )

    @Parcelize
    data class Response(
        val `data`: Data,
        val message: String,
        val status: Boolean,
        val statusCode: Int
    ): Parcelable {
        @Parcelize
        data class Data(
            val alamat: String,
            val deskripsi: String,
            val `file`: String,
            val id: String,
            val judul: String,
            val user: User.Data,
            val user_id: String
        ): Parcelable
    }

    data class ResponseAll(
        val `data`: List<Response.Data>,
        val message: String,
        val status: Boolean,
        val statusCode: Int
    )

}