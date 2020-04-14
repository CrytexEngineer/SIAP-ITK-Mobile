package com.example.siapitk.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    @SerializedName("MA_NamaLengkap")
    var MA_NamaLengkap: String,
//    @SerializedName("MA_Nrp'")
    var MA_Nrp:Int,
    @SerializedName("MA_Nrp'")
    var MA_NRP_Baru:Int,
    @SerializedName("MA_Email")
    var MA_email: String,
    @SerializedName("MA_IMEI")
    var MA_IMEI: String


)
