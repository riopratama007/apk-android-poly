package com.poly.core.data.source.remote.response

object Service {
    data class Request(
        val image: String,
        val pathFolderImage: String
    )

    data class Response(
        val result: Data,
        val status: Boolean,
        val message: String
    ) {
        data class Data(
            val image_path: String,
            val hashedFileName: String
        )
    }
}