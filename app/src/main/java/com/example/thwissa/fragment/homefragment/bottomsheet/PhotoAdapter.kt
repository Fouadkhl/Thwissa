package com.example.mybottomsheet

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thwissa.databinding.ItembottomcheetBinding


class PhotoAdapter(private val listOfPhotos: List<Uri> , private  val onClickListener: OnClickListener) :
    RecyclerView.Adapter<PhotoAdapter.ImageViewAdapter>() {

    inner class ImageViewAdapter(private val binding: ItembottomcheetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemImageUri: Uri) { // you delete position
            Glide.with(itemView)
                .load(itemImageUri)
                .into(binding.image)

            binding.image.setOnClickListener {
                onClickListener.onClick(itemImageUri)
                binding.checkb.isChecked = true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewAdapter {
        val binding =
            ItembottomcheetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewAdapter(binding)
    }

    override fun onBindViewHolder(holder: ImageViewAdapter, position: Int) {
        val itemString = listOfPhotos[position]
        holder.bind(itemString)
    }

    override fun getItemCount() = listOfPhotos.size

    class OnClickListener (val clickListener: (photoUri: Uri ) -> Unit){
        fun onClick (photoUri: Uri)=clickListener(photoUri)
    }
}