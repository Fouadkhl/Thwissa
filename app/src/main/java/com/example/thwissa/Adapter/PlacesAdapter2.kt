package com.example.thwissa.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.thwissa.R
import com.example.thwissa.databinding.ItemRegionBinding
import com.example.thwissa.dataclasses.WillayaStory
import com.example.thwissa.utils.Constants

class PlacesAdapter2(private val listofregions : ArrayList<WillayaStory>)
    : RecyclerView.Adapter<PlacesAdapter2.PlaceViewAdapter>()  {

        inner class PlaceViewAdapter (private val binding : ItemRegionBinding)
            : RecyclerView.ViewHolder(binding.root){
            val photo = binding.ivRegionItem
            val willaya = binding.tvRegion

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewAdapter {
        val itemBinding =ItemRegionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceViewAdapter(itemBinding)
    }

    override fun onBindViewHolder(holder: PlaceViewAdapter, position: Int) {
        val item = listofregions[position]
        holder.photo.setImageResource(item.image)
        holder.willaya.text = item.text

        if (item.text.equals("Coastal")) {
            holder.photo.setOnClickListener {
                val bundle  = bundleOf("one" to  1)
                Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_wilayasFragment , bundle)
            }

        }else if (item.text.equals("Hills")) {
            holder.photo.setOnClickListener {
                val bundle  = bundleOf("one" to 2)
                Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_wilayasFragment , bundle)
            }
        }else {
            holder.photo.setOnClickListener {
                val bundle  = bundleOf("one" to 3)
                Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_wilayasFragment , bundle)
            }
        }


    }

    override fun getItemCount() = listofregions.size
}