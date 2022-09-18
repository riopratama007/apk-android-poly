package com.poly.core.data.source.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

object Store {
    data class Request(
        val alamat: String,
        val noHp: String,
        val description: String,
        val user_id: String,
    )

    @Parcelize
    data class Response(
        val `data`: Data,
        val message: String,
        val status: Boolean,
        val statusCode: Int
    )  : Parcelable {
        @Parcelize
        data class Data(
            val result: Result? = null
        ) : Parcelable  {
            @Parcelize
            data class Result(
                val alamat: String,
                val description: String,
                val id: String,
                val noHp: String,
                val user_id: String
            ) : Parcelable
        }
    }

    @Parcelize
    data class ResponseData(
        val `data`: Data,
        val message: String,
        val status: Boolean,
        val statusCode: Int
    ) : Parcelable {
        @Parcelize
        data class Data(
            val id: String,
            val alamat: String,
            val noHp: String,
            val description: String,
            val user_id: String,
            val user: User.Data
        ) : Parcelable
    }
}