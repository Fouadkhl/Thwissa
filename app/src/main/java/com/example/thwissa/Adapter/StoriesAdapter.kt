package com.example.thwissa.Adapter

import android.view.LayoutInflater
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.thwissa.R
import com.example.thwissa.databinding.ItemAddStoryBinding
import com.example.thwissa.dataclasses.WillayaStory
import com.example.thwissa.fragment.homefragment.ModelHomeFragment


class StoriesAdapter(
    private val listofStories: List<WillayaStory>,
    private val homeviewmodel: ModelHomeFragment ,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<StoriesAdapter.StoryViewHolder>() {

    inner class StoryViewHolder(private val binding: ItemAddStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val photo = binding.ivImageStory
        val willaya = binding.tvStory
        val btnAddStory = binding.ivAddStory

        fun bind(story: WillayaStory) {
            binding.ivImageStory.setImageResource(story.image)
            binding.tvStory.text = story.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val itemBinding =
            ItemAddStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        if (position == 0) {

            holder.btnAddStory.visibility = VISIBLE
            holder.photo.setOnClickListener {
                homeviewmodel.setcanload()
            }
        } else {
            val item = listofStories[position]
            holder.bind(item)
            holder.photo.setOnClickListener {
                onClickListener.onClick(item)
//                it.findNavController().navigate(R.id.action_homeFragment_to_storyFragment)
            }
        }
    }


    override fun getItemCount() = listofStories.size

    class OnClickListener(val clickListener: (willayaStory: WillayaStory) -> Unit) {
        fun onClick(willayaStory: WillayaStory) = clickListener(willayaStory)
    }

}