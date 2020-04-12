

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.siapitk.ApiUtils.ProfileDataService
import com.example.siapitk.data.model.LoggedInUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MasterRepository {

    //Inisiasi Variable
    private val mKelas: MutableLiveData<ArrayList<Kelas>> by lazy {
        MutableLiveData<ArrayList<Kelas>>()
    }

    private val mLoggedInUserProfile: MutableLiveData<ArrayList<LoggedInUser>> by lazy {
        MutableLiveData<ArrayList<LoggedInUser>>()
    }

    //Membuat Retrofit Instance
    private val KelasDataService = RetrofitInstance.getRetrofitInstance().create(KelasDataService::class.java)
    private  val UserProfileDataService= RetrofitInstance.getRetrofitInstance().create(ProfileDataService::class.java)

    //Mengambil Data Dari API
    fun getKelas(MA_Nrp:Int): MutableLiveData<ArrayList<Kelas>> {

        val call: Call<ApiResponse> = KelasDataService.getKelas(MA_Nrp)
        val obj =  object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>?, response: Response<ApiResponse>?) {
                mKelas.value = response?.body()?.kelasList
                Log.d("SUCCESS", response?.body()?.properties.toString())
            }

            override fun onFailure(call: Call<ApiResponse>?, t: Throwable?) {
                Log.d("EROR", t.toString())
            }
        }
        call.enqueue(obj)
        return mKelas
    }

    fun getUserProfile(MA_Nrp:Int): MutableLiveData<ArrayList<LoggedInUser>> {
        val call: Call<ApiResponse> = UserProfileDataService.getUserProfile(MA_Nrp)
        val obj =  object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>?, response: Response<ApiResponse>?) {
                mLoggedInUserProfile.value = response?.body()?.loggedInUser
                Log.d("SUCCESS", response?.body()?.loggedInUser.toString())
            }

            override fun onFailure(call: Call<ApiResponse>?, t: Throwable?) {
                Log.d("EROR", t.toString())
            }
        }
        call.enqueue(obj)
        return mLoggedInUserProfile



    }
}