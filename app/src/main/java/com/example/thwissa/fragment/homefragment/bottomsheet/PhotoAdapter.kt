package com.example.mybottomsheet

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thwissa.databinding.ItembottomcheetBinding


class PhotoAdapter(private val listOfPhotos: List<Uri>) :
    RecyclerView.Adapter<PhotoAdapter.ImageViewAdapter>() {

    inner class ImageViewAdapter(private val binding: ItembottomcheetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemImageUri: Uri, position: Int) {
            binding.image.setOnClickListener {
                //
            }
            Glide.with(itemView)
                .load(itemImageUri)
                .into(binding.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewAdapter {
        val binding =
            ItembottomcheetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewAdapter(binding)
    }

    override fun onBindViewHolder(holder: ImageViewAdapter, position: Int) {
        val itemString = listOfPhotos[position]
        holder.bind(itemString, position)
    }

    override fun getItemCount() = listOfPhotos.size
}