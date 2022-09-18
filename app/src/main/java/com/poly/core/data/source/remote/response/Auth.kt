package com.poly.core.data.source.remote.response

object Auth {
    data class RequestRegister(
        val nama: String? = "",
        val alamat: String? = "",
        val no: String? = "",
        val email: String? = "",
        val password: String? = "",
        val confirmation_password: String? = "",
        val pendidikan: String? = "",
        val profesi: String? = "",
        val jenis_kelamin: String? = "",
        val image: String? = "",
    )

    data class RequestLogin(
        val phone_number: String,
        val password: String
    )

    data class Response(
        val access_token: AccessToken,
        val alamat: String,
        val email: String,
        val id: String,
        val image_url: String,
        val jenis_kelamin: String,
        val nama: String,
        val no: String,
        val password: String,
        val pendidikan: String,
        val profesi: String
    ) {
        data class AccessToken(
            val sub: String
        )
    }
}