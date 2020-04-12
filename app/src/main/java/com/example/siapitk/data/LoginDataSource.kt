package com.example.siapitk.data

import ApiResponse
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.siapitk.ApiUtils.LoginDataService
import com.example.siapitk.data.model.LoggedInUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */

private val mLoggedInLoggedInUser: MutableLiveData<ArrayList<LoggedInUser>> by lazy {
    MutableLiveData<ArrayList<LoggedInUser>>()
}

class LoginDataSource {


    fun login(username: String, password: String): Result<LoggedInUser> {
        
        val loginDataService=RetrofitInstance.getRetrofitInstance().create(LoginDataService::class.java)

        try {
            val call: Call<ApiResponse> = loginDataService.login(username, password,"fdfdfd")
            val obj = object : Callback<ApiResponse> {
                override fun onResponse(
                    call: Call<ApiResponse>?,
                    response: Response<ApiResponse>?
                ) {
                    mLoggedInLoggedInUser.value = response?.body()?.loggedInUser
                    Log.d("SUCCESS", response?.body()?.loggedInUser.toString())
                }

                override fun onFailure(call: Call<ApiResponse>?, t: Throwable?) {
                    Log.d("EROR", t.toString())
                }
            }
            call.enqueue(obj)
            return Result.Success(mLoggedInLoggedInUser.value?.get(0))

        } catch (e: Throwable) {
            Log.d("EROR", e.toString())
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}

