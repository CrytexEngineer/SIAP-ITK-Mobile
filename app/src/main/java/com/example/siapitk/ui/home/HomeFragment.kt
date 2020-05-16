package com.example.siapitk.ui.home

import HomeViewModel
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.siapitk.R
import com.example.siapitk.data.localDataSource.LoginPreferences
import com.example.siapitk.service.AlarmService
import com.example.siapitk.ui.KelasAdapter
import com.example.siapitk.ui.login.HomeViewModelFactory
import kotlin.math.log


class HomeFragment : Fragment() {
    private lateinit var adapter: KelasAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutInflater = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = layoutInflater.findViewById(R.id.rv_matakuliah)
        initRecyclerView()
        return layoutInflater
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         viewModel = ViewModelProviders.of(this,
            activity?.application?.let { HomeViewModelFactory(it) }).get(HomeViewModel::class.java)
        viewModel.kelas.observe(this, Observer { t ->
            t?.let {
                it.kelasList?.let { it1 -> adapter.setListKelas(it1) }
            }
        })
        activity?.application?.let { LoginPreferences(it).getLoggedInUser()?.MA_Nrp }?.let {
            Log.d("TAG",LoginPreferences(activity!!.application).getLoggedInUser().toString())
            showData(
                it
            )
        }
    }


    private fun initRecyclerView() {
        adapter = activity?.let { KelasAdapter(it) }!!
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    private fun showData(MA_Nrp: Int) {
        viewModel.getKelas(MA_Nrp)
    }
}
