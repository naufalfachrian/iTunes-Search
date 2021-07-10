package com.naufalfachrian.itunessearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naufalfachrian.itunessearch.databinding.MusicViewHolderBinding
import com.naufalfachrian.itunessearch.entity.Music
import com.naufalfachrian.itunessearch.utility.extension.setImageFromUrlString

class MusicAdapter(private val items: List<Music>) : RecyclerView.Adapter<MusicAdapter.ViewHolder>() {

    class ViewHolder(val binding: MusicViewHolderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MusicViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.music = items[position]
        holder.binding.musicAlbumArtView.setImageFromUrlString(items[position].albumArtUrl)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

}