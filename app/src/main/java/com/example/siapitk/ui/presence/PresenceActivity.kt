package com.example.siapitk.ui.presence

import Kelas
import PresenceViewModel
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.siapitk.R
import com.example.siapitk.ViewModel.PresenceViewModelFactory
import com.example.siapitk.data.localDataSource.LoginPreferences
import com.example.siapitk.ui.PresenceAdapter
import kotlinx.android.synthetic.main.activity_presence.*

class PresenceActivity : AppCompatActivity() {

    private lateinit var adapter: PresenceAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: PresenceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_presence)
        initRecyclerView()

        val tvPresenceCount = findViewById<TextView>(R.id.presence_tv_count)

        viewModel = ViewModelProviders.of(this, application?.let { PresenceViewModelFactory(it) })
            .get(PresenceViewModel::class.java)
        viewModel.presence.observe(this, Observer { t ->
            t?.let {
                Log.d("KRIWIL", it.presences.toString())
                it.presences?.let { it1 -> adapter.setListPresence(it1) }
            }
        })
        viewModel.presenceCount.observe(this, Observer { t ->
            t?.let {
                var count = it.presenceCount?.get(0)?.persentase?.times(100)
                if (count == null) {
                    presence_tv_count_meeting.text = "Belum ada pertemuan"
                } else {
                    tvPresenceCount.text = count.toString() + "%"
                    presence_tv_count_meeting.text =
                        "Dari " + it.presenceCount?.get(0)?.jumlahPertemuan + " pertemuan"
                }
            }
        })

        viewModel.errorMessege.observe(this, Observer { t ->
            t?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })


        application?.let { LoginPreferences(it).getLoggedInUser()?.MA_Nrp }?.let {
            intent.getParcelableExtra<Kelas>(this.getString(R.string.kelas_detail)).matakuliahID
                ?.let { it1 ->
                    showData(
                        it, it1
                    )
                }
        }
    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.rv_presence)
        adapter = PresenceAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun showData(MA_Nrp: Int, MK_ID: String) {
        viewModel.getPresences(MA_Nrp, MK_ID)
        viewModel.getPresenceCount(MA_Nrp, MK_ID)
    }
}
