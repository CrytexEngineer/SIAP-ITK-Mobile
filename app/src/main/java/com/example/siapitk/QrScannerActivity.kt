package com.example.siapitk

import ApiResponse
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.siapitk.ViewModel.QRScannerViewModel
import com.example.siapitk.ViewModel.QRScannerViewModelFactory
import com.example.siapitk.data.localDataSource.LoginPreferences
import com.example.siapitk.data.model.QRcode
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
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
    private lateinit var viewModel: QRScannerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scanner)
        Dexter.withActivity(this).withPermission(android.Manifest.permission.CAMERA)
            .withListener(this).check()

        btn_scanner_backward.setOnClickListener {finish()}
        btn_scanner_flashlight.setOnClickListener{
            scanner_view.flash = !scanner_view.flash
        }

        viewModel = ViewModelProviders.of(this, QRScannerViewModelFactory(application))
            .get(QRScannerViewModel::class.java)
        viewModel.validationToken.observe(this, Observer { t ->
            t?.let {
                if (t.token != null) {
                    showDialog(true, t)
                } else {
                    showDialog(false, t)
                }
                Log.d("TOKEN", t.token.toString())
            }
        })
        viewModel.registerMeetingStatus.observe(this, Observer { t ->
            t?.let {
                showDialog(false, t)
                Log.d("Registred", t.properties.toString())
            }

        })

        viewModel.errorMessege.observe(this, Observer { t ->
            t.let {
                Snackbar.make(root_scanner_acrivity, it, Snackbar.LENGTH_LONG)
            }
        })
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

    private fun rescan() {
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

        try {
            val jsonParser = JsonParser()
            val jsonTree = jsonParser.parse(p0?.text.toString())
            if (jsonTree.isJsonObject) {
                val jsonObject = jsonTree.asJsonObject
                val VD_PT_ID = jsonObject.get("VD_PT_ID").asString
                val VD_Token = jsonObject.get("VD_Token").asString
                val qrCode = QRcode(VD_PT_ID, VD_Token)
                viewModel.getValidation(qrCode)
        }
    }
        catch (e: JsonSyntaxException){
            Toast.makeText(this, e.message,Toast.LENGTH_LONG)
        }

    }

    private fun registerMeeting(MA_Nrp: Int, PT_Token: String) {
        viewModel.registerMeeting(MA_Nrp, PT_Token)
    }


    private fun showDialog(hasToken: Boolean, response: ApiResponse) {
        val bottomSheetDialog = BottomSheetDialog(this)
        Log.d("DIALOG", hasToken.toString())
        if (hasToken) {
            bottomSheetDialog.setContentView(R.layout.validation_confirm_dialog)
            val mk_mtakuliah = bottomSheetDialog.findViewById<TextView>(R.id.mk_mata_kuliah)
            val ke_kelas = bottomSheetDialog.findViewById<TextView>(R.id.tv_validatiton_ke_kelas)
            val pe_nip_pengajar = bottomSheetDialog.findViewById<TextView>(R.id.pe_nama_lengkap)
            val button_confirn =
                bottomSheetDialog.findViewById<MaterialButton>(R.id.validation_presence_button)

            mk_mtakuliah?.text = response.token?.get(0)?.namaMatakuliah
            ke_kelas?.text = response.token?.get(0)?.namaKelas
            pe_nip_pengajar?.text = response.token?.get(0)?.namaPengajar

            val token = response.token?.get(0)?.token
            button_confirn?.setOnClickListener {
                LoginPreferences(application).getLoggedInUser()?.MA_Nrp?.let { it1 ->
                    registerMeeting(
                        it1, token!!
                    )
                }
                bottomSheetDialog.dismiss()
            }

        } else {
            bottomSheetDialog.setContentView(R.layout.validatiom_notification_dialog)
            val msg = bottomSheetDialog.findViewById<TextView>(R.id.validation_messege)
            val buttonRescan =
                bottomSheetDialog.findViewById<MaterialButton>(R.id.btn_notification_rescan)
            buttonRescan?.setOnClickListener {
                rescan()
                bottomSheetDialog.dismiss()
            }
            msg?.text = response.properties?.get(0)?.msg
            bottomSheetDialog.setOnCancelListener {
                rescan()
            }
        }

        bottomSheetDialog.setCancelable(true)
        bottomSheetDialog.show()

    }


}







