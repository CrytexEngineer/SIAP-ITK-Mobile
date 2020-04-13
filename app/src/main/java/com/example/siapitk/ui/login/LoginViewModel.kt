package com.example.siapitk.ui.login

import ApiResponse
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.siapitk.R
import com.example.siapitk.data.GetListUsersCallback
import com.example.siapitk.data.LoginRepository

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String, imei:String) {
        // can be launched in a separate asynchronous job

        loginRepository.login(username, password, imei, object : GetListUsersCallback {
            override fun onSuccess(data: ApiResponse) {
                var loggedInUser = data.loggedInUser?.get(0)

                if (loggedInUser != null) {
                    _loginResult.value =
                        LoginResult(success = loggedInUser?.MA_NamaLengkap?.let {
                            LoggedInUserView(
                                displayName = it
                            )
                        })
                } else {
                    _loginResult.value = LoginResult(error = data?.properties?.get(0)?.msg)
                }
            }

            override fun onFailed(errorMessage: String?) {
                TODO("Not yet implemented")
            }

        })


    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
