package com.example.thwissa.Adapter

import android.view.LayoutInflater
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.thwissa.R
import com.example.thwissa.databinding.ItemAddStoryBinding
import com.example.thwissa.dataclasses.WillayaStory
import com.example.thwissa.fragment.homefragment.ModelHomeFragment


class StoriesAdapter (private val activity: FragmentActivity, private val listofStories : List<WillayaStory>, private val homeviewmodel : ModelHomeFragment)
    : RecyclerView.Adapter<StoriesAdapter.StoryViewHolder>()  {

        inner class StoryViewHolder (private val binding : ItemAddStoryBinding)
            : RecyclerView.ViewHolder(binding.root){
                val photo = binding.ivImageStory
                val willaya = binding.tvStory
                val btnAddStory = binding.ivAddStory
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val itemBinding =ItemAddStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        if (position == 0) {

            holder.btnAddStory.visibility = VISIBLE
            holder.photo.setOnClickListener{
                homeviewmodel.setcanload()

            }
        }else{
            val item = listofStories[position]
            holder.photo.setImageResource(item.image)
            holder.willaya.text = item.text
            holder.photo.setOnClickListener {
                it.findNavController().navigate(R.id.action_homeFragment_to_storyFragment)
            }
        }
    }


    override fun getItemCount() = listofStories.size

}