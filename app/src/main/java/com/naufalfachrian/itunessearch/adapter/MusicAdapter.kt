package com.naufalfachrian.itunessearch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naufalfachrian.itunessearch.databinding.MusicViewHolderBinding
import com.naufalfachrian.itunessearch.entity.Music
import com.naufalfachrian.itunessearch.utility.extension.setImageFromUrlString

class MusicAdapter(private val items: List<Music>, private val callback: Callback) : RecyclerView.Adapter<MusicAdapter.ViewHolder>() {

    class ViewHolder(val binding: MusicViewHolderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun showPlayingIndicator() {
            binding.isPlayingIndicatorView.visibility = View.VISIBLE
        }

        fun hidePlayingIndicator() {
            binding.isPlayingIndicatorView.visibility = View.GONE
        }

    }

    interface Callback {
        fun onItemMusicClickListener(music: Music)
    }

    private var lastPlayedMusicPosition: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MusicViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val music = items[position]
        holder.binding.music = music
        holder.binding.musicAlbumArtView.setImageFromUrlString(music.albumArtUrl)
        holder.hidePlayingIndicator()
        if (lastPlayedMusicPosition == position) {
            holder.showPlayingIndicator()
        }
        holder.binding.root.setOnClickListener {
            callback.onItemMusicClickListener(music)
            clearPlayingMusicIndicator()
            lastPlayedMusicPosition = position
            holder.showPlayingIndicator()
        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    private fun clearPlayingMusicIndicator() {
        val refreshPosition = lastPlayedMusicPosition
        lastPlayedMusicPosition = null
        if (refreshPosition != null) {
            notifyItemChanged(refreshPosition)
        }
    }

}