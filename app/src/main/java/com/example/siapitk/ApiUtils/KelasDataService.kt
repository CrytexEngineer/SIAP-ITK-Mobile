
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface KelasDataService {
    @GET("kelas/{id}")
    fun getKelas(@Path("id") id:Int): Call<ApiResponse>
}