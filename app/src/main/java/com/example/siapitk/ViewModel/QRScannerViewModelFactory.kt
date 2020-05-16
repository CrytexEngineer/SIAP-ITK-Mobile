package com.example.siapitk.ViewModel

import RetrofitInstance
import ValidationDataSource
import ValidatorDataService
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.siapitk.data.QRScannerRepository


class QRScannerViewModelFactory(var application: Application) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QRScannerViewModel::class.java)) {
            return QRScannerViewModel(
                repository = QRScannerRepository(
                    dataSource = ValidationDataSource(
                        validatorDataService =
                        RetrofitInstance.getRetrofitInstance()
                            .create(ValidatorDataService::class.java)
                    ),
                    application = application
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}