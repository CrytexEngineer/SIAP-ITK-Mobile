package com.example.siapitk.ui.notification

import ApiResponse
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.siapitk.data.NotificationRepository
import com.example.siapitk.data.RemoteDataCallback


class NotificationViewModel(private val repository: NotificationRepository) : ViewModel() {

    val userNotification = MutableLiveData<ApiResponse>()
    val errorMessege = MutableLiveData<String>()


    fun getNotification(MA_Nrp: Int) {
        repository.getNotification(MA_Nrp, object : RemoteDataCallback {
            override fun onSuccess(data: ApiResponse) {
                userNotification.value = data
            }

            override fun onFailed(errorMessage: String?) {
                errorMessege.value = errorMessage
            }
        })

    }
}