package com.poly.core.data.source.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

object Business {
    data class Request(
        val keterangan: String,
        val file: String,
        val lokasi: String,
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
            val `file`: String,
            val id: String,
            val keterangan: String,
            val lokasi: String,
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