package com.example.mybottomsheet

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thwissa.databinding.ItembottomcheetBinding
import com.google.android.material.shape.CornerFamily

private var imageCornerStateleft  =true
    private var imageCornerStateright = true

class PhotoAdapter(private val listOfPhotos: List<Uri>) :
    RecyclerView.Adapter<PhotoAdapter.ImageViewAdapter>() {

    inner class ImageViewAdapter(private val binding: ItembottomcheetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemImageUri: Uri ,  position: Int) {
            if(position == 0 && imageCornerStateleft) {
                binding.image.shapeAppearanceModel = binding.image.shapeAppearanceModel
                    .toBuilder()
                    .setTopLeftCorner(CornerFamily.ROUNDED, 15.0f)
                    .build()
                imageCornerStateleft = false
            }else if (position == 2 && imageCornerStateright) {
                binding.image.shapeAppearanceModel = binding.image.shapeAppearanceModel
                    .toBuilder()
                    .setTopRightCorner(CornerFamily.ROUNDED, 15.0f)
                    .build()
                imageCornerStateright = false
            }
            binding.image.setOnClickListener {
                // when click the item
            }
            Glide.with(itemView)
                .load(itemImageUri)
                .into(binding.image )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewAdapter {
        val binding = ItembottomcheetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewAdapter(binding)
    }

    override fun onBindViewHolder(holder: ImageViewAdapter, position: Int) {
        val itemString = listOfPhotos[position]
        holder.bind(itemString , position)
    }

    override fun getItemCount() = listOfPhotos.size
}