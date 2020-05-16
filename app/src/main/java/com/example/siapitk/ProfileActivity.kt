package com.example.siapitk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.siapitk.data.localDataSource.LoginPreferences
import com.example.siapitk.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val loggedInUser = LoginPreferences(application).getLoggedInUser()
        profile_name.text = loggedInUser?.MA_NamaLengkap
        profile_ma_email.text = loggedInUser?.MA_email
        profile_ma_nrp.text = loggedInUser?.MA_Nrp.toString()
        profile_ma_imei.text = loggedInUser?.MA_IMEI.toString()
        profile_picture.text = loggedInUser?.MA_NamaLengkap?.get(0).toString()
        btn_profile_back.setOnClickListener({ finish() })
        btn_profile_change_password.setOnClickListener {

            Log.d("MOVE", "MOVE")
            startActivity(
                Intent(
                    this,
                    ResetPasswordActivity::class.java
                )
            )
        }

        btn_profile_log_out.setOnClickListener {
            LoginPreferences(this).clearUserPreferences()
            val intent = Intent(this, LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP )
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent)
            finish()
        }
    }

}

