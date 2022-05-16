package com.example.thwissa.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.thwissa.R
import com.example.thwissa.databinding.ItemHotelBinding
import com.example.thwissa.dataclasses.WillayaStory

class NearToYouRecyclerViewAdapter(private val listofregions: ArrayList<WillayaStory>) :
    RecyclerView.Adapter<NearToYouRecyclerViewAdapter.NearViewAdapter>() {


    inner class NearViewAdapter(private val binding: ItemHotelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val photo = binding.ivHotelPlacesAdapter
        val willaya = binding.tvNamePlaceHotel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearViewAdapter {
        val itemBinding =
            ItemHotelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NearViewAdapter(itemBinding)
    }

    override fun onBindViewHolder(holder: NearViewAdapter, position: Int) {
        val item = listofregions[position]
        holder.photo.setImageResource(item.image)
        holder.willaya.text = item.text
        holder.photo.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_overview)
        }
    }

    override fun getItemCount() = listofregions.size
}
