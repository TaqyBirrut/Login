package com.example.login.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.login.databinding.ItemStoryBinding
import com.example.login.response.ListStoryItem

class StoryAdapter(private val data:MutableList<ListStoryItem> = mutableListOf(), private val listener:(ListStoryItem) -> Unit): RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    fun setData(data: MutableList<ListStoryItem>){
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class StoryViewHolder(private val v: ItemStoryBinding): RecyclerView.ViewHolder(v.root) {
        fun bind(itemStoryBinding: ListStoryItem){
            v.ivStoryPhoto.load(itemStoryBinding.photoUrl)
            v.tvStoryName.text = itemStoryBinding.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder =
        StoryViewHolder(ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{
            listener(item)
        }
    }

    override fun getItemCount(): Int = data.size
}