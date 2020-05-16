package com.example.siapitk.data

import ApiResponse

interface RemoteDataCallback {
        fun onSuccess(data: ApiResponse)
        fun onFailed(errorMessage: String?)
    }
