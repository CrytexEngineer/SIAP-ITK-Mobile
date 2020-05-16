package com.example.siapitk.ui.login

import HomeRepository
import HomeViewModel
import KelasDataService
import RetrofitInstance
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.siapitk.data.remoteDataSource.KelasDataSource

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class HomeViewModelFactory(var application: Application) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                repository = HomeRepository(
                    dataSource = KelasDataSource(
                        kelasDataService =
                        RetrofitInstance.getRetrofitInstance().create(KelasDataService::class.java)
                    ),
                    application = application
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
