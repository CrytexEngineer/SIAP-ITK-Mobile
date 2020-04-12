package com.example.siapitk.ApiUtils

import ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileDataService {
    @GET("profile/{id}")
    fun getUserProfile(@Path("id") id:Int): Call<ApiResponse>
}