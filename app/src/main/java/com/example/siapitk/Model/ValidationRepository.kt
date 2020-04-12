import android.util.Log
import  androidx.lifecycle.MutableLiveData
import com.example.siapitk.Model.QRcode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val mApiResponse: MutableLiveData<ApiResponse> by lazy {
    MutableLiveData<ApiResponse>()
}


class ValidationUtils {
    private val ValidatorDataService =
        RetrofitInstance.getRetrofitInstance().create(ValidatorDataService::class.java)

    fun getValidation(qrCode: QRcode): MutableLiveData<ApiResponse> {

        mApiResponse.value = null

        val call: Call<ApiResponse> = ValidatorDataService.getValidation(qrCode)
        val obj = object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>?, response: Response<ApiResponse>?) {
                Log.d("RESPONSE'", response?.body()?.properties.toString())
                Log.d("RESPONSE'", response?.body()?.token.toString())
                mApiResponse.value = response?.body()
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.d("EROR", t.message)
            }


        }
        call.enqueue(obj)
        return mApiResponse
    }


    fun registerMeeting(MA_Nrp: Int, PT_Token: Int): MutableLiveData<ApiResponse> {
        mApiResponse.value = null
        val call: Call<ApiResponse> = ValidatorDataService.registerMeeting(MA_Nrp, PT_Token)
        val obj = object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>?, response: Response<ApiResponse>?) {
                Log.d("RESPONSE'", response?.toString())
                mApiResponse.value = response?.body()
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {

            }

        }
        call.enqueue(obj)
        return mApiResponse
    }

}
