package com.example.siapitk.ApiUtils

import ApiResponse
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface LoginDataService {

    @POST("login")
    @Multipart
    fun login(
        @Part("MA_Email") MA_Email: String,
        @Part("MA_PASSWORD") MA_PASSWORD: String,
        @Part("MA_IMEI") MA_IMEI: String
    ): Call<ApiResponse>

    fun logout() {
        // TODO: revoke authentication
    }


}