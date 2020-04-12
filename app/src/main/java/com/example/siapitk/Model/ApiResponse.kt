

import com.example.siapitk.data.model.LoggedInUser
import com.google.gson.annotations.SerializedName

class ApiResponse {


    @SerializedName("kelas")
     var kelasList: ArrayList<Kelas>?=null

    @SerializedName("properties")
    var properties: ArrayList<Properties>?=null

    @SerializedName("token")
     var token: ArrayList<Token>?=null

    @SerializedName("user")
    var loggedInUser: ArrayList<LoggedInUser>?=null
}