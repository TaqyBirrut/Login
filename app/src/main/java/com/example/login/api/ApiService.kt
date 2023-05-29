package com.example.login.api

import com.example.login.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @JvmSuppressWildcards
    @GET("stories")
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLVpSMnBFTDlCNkxrMXFybDYiLCJpYXQiOjE2ODUzNjk0MDl9.tz4ME32BJckoEDP9k-zv9kmvrVyJ-ZdcA3to8D9SjBo")
    suspend fun stories(): StoryResponse

    @JvmSuppressWildcards
    @GET("stories/{id}")
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLVpSMnBFTDlCNkxrMXFybDYiLCJpYXQiOjE2ODUzNjk0MDl9.tz4ME32BJckoEDP9k-zv9kmvrVyJ-ZdcA3to8D9SjBo")
    suspend fun storiesDetail(@Path("id")id: String): DetailResponse

    @Multipart
    @POST("stories")
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLVpSMnBFTDlCNkxrMXFybDYiLCJpYXQiOjE2ODUzNjk0MDl9.tz4ME32BJckoEDP9k-zv9kmvrVyJ-ZdcA3to8D9SjBo")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): AddPostResponse

}