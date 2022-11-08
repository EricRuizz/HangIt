package com.example.hangit.nasa

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hangit.databinding.ItemNasaImageBinding

class NasaRecyclerViewAdapter: RecyclerView.Adapter<NasaRecyclerViewAdapter.NasaVH> {

    private var photoList: List<NasaImage>? = null
    inner class NasaVH(binding: ItemNasaImageBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NasaVH {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: NasaVH, position: Int) {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNasaImageBinding.inflate(inflater,parent,false)
        return NasaVH(binding)
    }

    override fun getItemCount(): Int {
        return photoList?.size?:0
    }
}