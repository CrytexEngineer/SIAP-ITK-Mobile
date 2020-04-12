package com.example.siapitk

import ApiViewModel
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.siapitk.data.model.LoggedInUser
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val apiRequetsViewModel = ViewModelProviders.of(this).get(ApiViewModel::class.java)
        apiRequetsViewModel.getUserProfile(1116110003)
            .observe(this, Observer<ArrayList<LoggedInUser>> { t ->

                t?.let {
                    profile_name.text = t[0].MA_NamaLengkap
                    profile_ma_email.text = t[0].MA_email
                    profile_ma_nrp.text = t[0].MA_Nrp.toString()
                    profile_ma_imei.text = t[0].MA_IMEI.toString()
                }
            })

    }
}
