

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.siapitk.data.RemoteDataCallback


class HomeViewModel( val repository: HomeRepository) : ViewModel() {

     val  kelas= MutableLiveData<ApiResponse>()

    fun getKelas(MA_Nrp: Int) {
        repository.getKelas(MA_Nrp, object : RemoteDataCallback {
            override fun onSuccess(data: ApiResponse) {
                kelas.value=data
            }

            override fun onFailed(errorMessage: String?) {

            }
        })
    }
    }

