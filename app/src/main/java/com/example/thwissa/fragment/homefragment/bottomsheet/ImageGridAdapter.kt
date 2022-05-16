//package com.example.mybottomsheet
//
//import android.content.Context
//import android.net.Uri
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.BaseAdapter
//
//
//
//class ImageGridAdapter(private val context : Context , private val listOfPhotos: List<Uri>)  :
//    BaseAdapter() {
//
//    override fun getCount()= listOfPhotos.size
//    override fun getItem(p0: Int)= null
//    override fun getItemId(p0: Int): Long =0
//
//    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
//        val holder: MyViewHolder
//        if (p1 == null) {
//            val itemBinding = com.example.shape.databinding.ItembottomcheetBinding.inflate(
//                LayoutInflater.from(p2?.context ?: p2?.context ), p2, false)
//
//            holder = MyViewHolder(itemBinding)
//            holder.view = itemBinding.root
//            holder.view.setTag(holder)
//        } else {
//            holder = p1.tag as MyViewHolder
//        }
//        var imageItem = listOfPhotos[p0]
//
//        Glide.with(holder.view)
//            .load(imageItem)
//            .into(holder.image)
//
//        return holder.view
//    }
//
//    inner class MyViewHolder  constructor(val binding: ItembottomcheetBinding) {
//        var view: View
//        val image = binding.image
//        init {
//            view = binding.root
//        }
//    }
//}