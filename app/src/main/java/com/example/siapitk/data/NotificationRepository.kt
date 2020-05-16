package com.example.siapitk.data

import ApiResponse
import android.app.Application
import com.example.siapitk.data.remoteDataSource.NotificationDataSource

class NotificationRepository(val dataSource: NotificationDataSource, application: Application) {
    fun getNotification(MA_Nrp: Int, callback: RemoteDataCallback) {
        dataSource.getNotification(MA_Nrp, object : RemoteDataCallback {
            override fun onSuccess(data: ApiResponse) {
                callback.onSuccess(data)
            }

            override fun onFailed(errorMessage: String?) {
                callback.onFailed(errorMessage)
            }
        })

    }


}