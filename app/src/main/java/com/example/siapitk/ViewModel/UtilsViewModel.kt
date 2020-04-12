package com.example.siapitk.ViewModel

import ApiResponse
import ValidationUtils
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.siapitk.Model.QRcode

class UtilsViewModel(application: Application) : AndroidViewModel(application) {
    private val validationUtils = ValidationUtils()

    fun getValidation(qrCode: QRcode): LiveData<ApiResponse> {
        return validationUtils.getValidation(qrCode)
    }

    fun regsterMeeting(MA_Nrp:Int,PT_Token:Int): LiveData<ApiResponse> {
        return validationUtils.registerMeeting(MA_Nrp,PT_Token)
    }

}