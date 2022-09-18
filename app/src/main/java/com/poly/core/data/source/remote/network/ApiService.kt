package com.poly.core.data.source.remote.network

import com.poly.core.data.source.remote.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("service/file")
    suspend fun serviceFileImage(
        @Body request: Service.Request
    ): Service.Response

    @POST("auth/register")
    suspend fun register(
        @Body requestRegister: Auth.RequestRegister
    ): Auth.Response

    @POST("auth/login")
    suspend fun login(
        @Body requestLogin: Auth.RequestLogin
    ): Auth.Response

    // Start Point Lowongan
    @GET("lowongan")
    suspend fun job(): Job.ResponseAll

    @POST("lowongan")
    suspend fun job(
        @Body request: Job.Request
    ): Job.Response

    @PATCH("lowongan/{id}")
    suspend fun job(
        @Path("id") id: String,
        @Body request: Job.Request
    ): Job.Response

    @DELETE("lowongan/{id}")
    suspend fun job(
        @Path("id") id: String
    ): Job.Response
    // End Point Lowongan

    // Start Point Business
    @GET("bisnis")
    suspend fun business(): Business.ResponseAll

    @POST("bisnis")
    suspend fun business(
        @Body request: Business.Request
    ): Business.Response

    @PATCH("bisnis/{id}")
    suspend fun business(
        @Path("id") id: String,
        @Body request: Business.Request
    ): Business.Response

    @DELETE("bisnis/{id}")
    suspend fun business(
        @Path("id") id: String
    ): Business.Response
    // End Point Business

    // Start Point Store
    @GET("toko/me/{user_id}")
    suspend fun store(
        @Path("user_id") userId: String
    ): Store.Response

    @POST("toko")
    suspend fun store(
        @Body request: Store.Request
    ): Store.ResponseData

    @PATCH("toko/{id}")
    suspend fun store(
        @Path("id") id: String,
        @Body request: Store.Request
    ): Store.ResponseData
    // End Point Store

    // Start Point Moment
    @GET("post")
    suspend fun moment(): Moment.ResponseAll

    @POST("post")
    suspend fun moment(
        @Body request: Moment.Request
    ): Moment.Response

    @PATCH("post/{id}")
    suspend fun moment(
        @Path("id") id: String,
        @Body request: Moment.Request
    ): Moment.Response

    @DELETE("post/{id}")
    suspend fun moment(
        @Path("id") id: String
    ): Moment.Response

    @Multipart
    @POST("post/multiple/file")
    suspend fun postMultiple(
        @Part("user_id") userId: RequestBody,
        @Part("lokasi") location: RequestBody,
        @Part("keterangan") description: RequestBody,
        @Part files: List<MultipartBody.Part>,
    ): Moment.Response
    // End Point Moment
}