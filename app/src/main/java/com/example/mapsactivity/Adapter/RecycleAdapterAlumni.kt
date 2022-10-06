package com.example.mapsactivity.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mapsactivity.R
import com.example.mapsactivity.data.Alumni

class RecycleAdapterAlumni : RecyclerView.Adapter<RecycleAdapterAlumni.ViewHolder>() {

    var listAlumni: List<Alumni>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_data_alumni, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listAlumni?.get(position)

        data?.let { alumni ->
            holder.nama.text = alumni.nama
            holder.tahun.text = alumni.tahun
        }
    }


    override fun getItemCount(): Int = listAlumni?.size ?: 0

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val nama: TextView = item.findViewById(R.id.tv_nama)
        val tahun: TextView = item.findViewById(R.id.tv_tahun)
    }


}