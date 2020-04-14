package com.example.siapitk.data

import ApiResponse
import android.app.Application
import androidx.preference.PreferenceManager
import com.example.siapitk.data.model.LoggedInUser


/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource, var application: Application) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    fun login(username: String, password: String, imei: String, callback: GetListUsersCallback) {
        // handle login

        dataSource.login(username, password, imei, object : GetListUsersCallback {
            override fun onSuccess(data: ApiResponse) {
                callback.onSuccess(data)
                data.loggedInUser?.get(0)?.let { setLoggedInUser(it) }
            }

            override fun onFailed(errorMessage: String?) {
                callback.onFailed(errorMessage)
            }


        })


//
//        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        var userData = PreferenceManager.getDefaultSharedPreferences(application).edit();
        userData.putString("MA_NamaLengkap", loggedInUser.MA_NamaLengkap)
        userData.putInt("MA_NRP_Baru", loggedInUser.MA_NRP_Baru)
        userData.putString("MA_email", loggedInUser.MA_email)
        userData.putInt("MA_Nrp", loggedInUser.MA_Nrp)
        userData.putString("MA_IMEI", loggedInUser.MA_IMEI)
        userData.apply()


    }
}
