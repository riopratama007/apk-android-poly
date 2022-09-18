package com.poly.core.data.source.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

object Moment {
    data class Request(
        val keterangan: String,
        val fileUrl: String,
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
            val id: String,
            val keterangan: String,
            val lokasi: String,
            val user: User.Data,
            val postFiles: List<PostFiles>,
            val user_id: String
        ): Parcelable {
            @Parcelize
            data class PostFiles(
                val id: String,
                val file_url: String,
                val type: String,
                val postId: String
            ): Parcelable
        }
    }

    data class ResponseAll(
        val `data`: List<Response.Data>,
        val message: String,
        val status: Boolean,
        val statusCode: Int
    )
}