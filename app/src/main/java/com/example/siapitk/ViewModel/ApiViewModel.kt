

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.siapitk.data.model.LoggedInUser


class ApiViewModel(application: Application) : AndroidViewModel(application) {
    private val masterRepository = MasterRepository()

    fun getKelas(MA_Nrp:Int): LiveData<ArrayList<Kelas>> {
        return masterRepository.getKelas(MA_Nrp)
    }

    fun getUserProfile(MA_Nrp:Int): LiveData<ArrayList<LoggedInUser>> {
        return masterRepository.getUserProfile(MA_Nrp)
    }

}