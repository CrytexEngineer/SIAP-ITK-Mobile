package com.example.siapitk

import ApiResponse
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.siapitk.Model.QRcode
import com.example.siapitk.ViewModel.UtilsViewModel
import com.google.gson.JsonParser
import com.google.zxing.Result
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_qr_scanner.*
import me.dm7.barcodescanner.zxing.ZXingScannerView


class QrScannerActivity : AppCompatActivity(), PermissionListener, ZXingScannerView.ResultHandler {
    lateinit var utilsViewModel: UtilsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scanner)
        utilsViewModel = ViewModelProviders.of(this).get(UtilsViewModel::class.java)
        Dexter.withActivity(this).withPermission(android.Manifest.permission.CAMERA)
            .withListener(this).check()

    }


    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        scanner_view.setResultHandler(this)
        scanner_view.setAutoFocus(true)
        scanner_view.startCamera()
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {
        TODO("Not yet implemented")
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this, "Izinkan Penggunaan Kamera", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        scanner_view.stopCamera()
    }

    fun rescan() {
        scanner_view.startCamera()
        if (scanner_view != null) {
            scanner_view.resumeCameraPreview(this)
        }
    }

    override fun handleResult(p0: Result?) {

        if (p0 != null) {
            executeValidation(p0)
        }


    }

    private fun executeValidation(p0: Result?) {
        val jsonParser = JsonParser()
        val jsonTree = jsonParser.parse(p0?.text.toString())
        val jsonObject = jsonTree.asJsonObject
        val VD_PT_ID = jsonObject.get("VD_PT_ID").asString
        val VD_Token = jsonObject.get("VD_Token").asString
        val qrCode = QRcode(VD_PT_ID, VD_Token)
        utilsViewModel.getValidation(qrCode).observe(this, Observer<ApiResponse> { t ->


            t?.let {
                if (t.token != null) {
                    showDialog(true, t)
                } else {

                    showDialog(false, t)
                }
                Log.d("TOKEN", t.token.toString())
                //        Log.d("Show", response?.toString())

            }
        })


    }

    private fun registerMeeting(MA_Nrp: Int, PT_Token: Int) {
        utilsViewModel.regsterMeeting(MA_Nrp, PT_Token).observe(this, Observer<ApiResponse> { t ->


            t?.let {
                showDialog(false, t)
                Log.d("Registred", t.properties.toString())
            }

        })
    }

    fun showDialog(hasToken: Boolean, response: ApiResponse) {
        val popUpDialog = Dialog(ContextThemeWrapper(this, R.style.DialogSlideAnim))

        Log.d("DIALOG", hasToken.toString())
        if (hasToken) {
            popUpDialog.setContentView(R.layout.validation_confirm_dialog)
            val mk_mtakuliah = popUpDialog.findViewById<TextView>(R.id.mk_mata_kuliah)
            val ke_kelas = popUpDialog.findViewById<TextView>(R.id.ke_kelas)
            val pe_nip_pengajar = popUpDialog.findViewById<TextView>(R.id.pe_nama_lengkap)
            val button = popUpDialog.findViewById<TextView>(R.id.validation_presence_button)

            mk_mtakuliah.text = response.token?.get(0)?.namaMatakuliah
            ke_kelas.text = response.token?.get(0)?.namaKelas
            pe_nip_pengajar.text = response.token?.get(0)?.namaPengajar

            var token = response.token?.get(0)?.token
            button.setOnClickListener(View.OnClickListener { it ->
                registerMeeting(1116110022, token!!)
                popUpDialog.dismiss()
            })

        } else {
            popUpDialog.setContentView(R.layout.validatiom_notification_dialog)
            val msg = popUpDialog.findViewById<TextView>(R.id.validation_messege)
            msg.text = response?.properties?.get(0)?.msg

        }

        popUpDialog.window?.setBackgroundDrawable(ColorDrawable(Color.argb(100, 0, 0, 0)));
        popUpDialog.window?.setGravity(Gravity.BOTTOM)
        popUpDialog.window?.setLayout(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        popUpDialog.setCancelable(true)
        popUpDialog.show()

    }


}







