package com.example.siapitk.ui.home

import ApiViewModel
import Kelas
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
import com.example.siapitk.ui.KelasAdapter


class HomeFragment : Fragment() {
    lateinit var adapter: KelasAdapter
    lateinit var recyclerView: RecyclerView

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
        showData(1116110003)


    }


    private fun initRecyclerView() {

        adapter = activity?.let { KelasAdapter(it) }!!
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

    }

    private fun showData(MA_Nrp:Int) {
        val apiRequetsViewModel = ViewModelProviders.of(this).get(ApiViewModel::class.java)
        apiRequetsViewModel.getKelas(MA_Nrp).observe(this, Observer<ArrayList<Kelas>> { t ->

            t?.let {
                adapter.setListKelas(it)
            }
        })


    }
}
