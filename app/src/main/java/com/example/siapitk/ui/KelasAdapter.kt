package com.example.siapitk.ui

import Kelas
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.siapitk.R
import kotlinx.android.extensions.LayoutContainer
import java.util.*

class KelasAdapter(context: Context) :
    RecyclerView.Adapter<KelasAdapter.KelasAdapater>() {

    class KelasAdapater(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {


        fun bindItem(kelas: Kelas, listener: (Kelas) -> Unit) {
            containerView.findViewById<TextView>(R.id.item_kelas_jam).text =
                kelas.jamMulai + "-" + kelas.jamUsai
            containerView.findViewById<TextView>(R.id.item_kelas_kelas).text = kelas.kelas
            containerView.findViewById<TextView>(R.id.item_kelas_matakuliah).text = kelas.mataKuliah
            containerView.findViewById<TextView>(R.id.item_kelas_ruang).text = kelas.jadwalRuangan
            containerView.findViewById<TextView>(R.id.item_kelas_dosen).text = kelas.namaPengajar

//            containerView.setOnClickListener { listener(Kelas)
        }

    }
    private var mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mListKelass: List<Kelas>? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): KelasAdapater {
        val itemView: View = mInflater.inflate(R.layout.item_kelas, p0, false)
        return KelasAdapater(itemView)
    }


    override fun getItemCount(): Int {
        return mListKelass?.size ?: 0
    }

    override fun onBindViewHolder(p0: KelasAdapater, p1: Int) {
//        val i = Intent(activity, DetailActivity::class.java)
//        i.putExtra(context.getString(R.string.Kelas_DETAIL), mListKelass?.get(p1))
      mListKelass?.get(p1)?.let {
          p0.bindItem(it) {
//                  Kelas: Kelas ->
//                activity.startActivity(i)
         }
//
//
        }
    }

    fun setListKelas(mListKelass: ArrayList<Kelas>) {
        this.mListKelass = mListKelass
        Log.d("ADAPTER",mListKelass.toString())
        notifyDataSetChanged()

    }
}

