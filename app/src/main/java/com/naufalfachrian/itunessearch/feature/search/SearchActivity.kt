package com.naufalfachrian.itunessearch.feature.search

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufalfachrian.itunessearch.adapter.MusicAdapter
import com.naufalfachrian.itunessearch.databinding.SearchActivityBinding
import com.naufalfachrian.itunessearch.entity.Music
import com.naufalfachrian.itunessearch.utility.wrapper.mediaplayer.MediaPlayerWrapper
import com.naufalfachrian.itunessearch.utility.wrapper.mediaplayer.MediaPlayerWrapperImpl

class SearchActivity : AppCompatActivity(), MusicAdapter.Callback, MediaPlayerWrapper.Callback {

    private lateinit var binding: SearchActivityBinding

    private lateinit var mediaPlayer: MediaPlayerWrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SearchActivityBinding.inflate(LayoutInflater.from(this))
        binding.lifecycleOwner = this
        binding.viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        binding.viewModel?.query?.observe(this, this::queryValueChanged)
        binding.viewModel?.musics?.observe(this, this::musicsValueChanged)
        binding.viewModel?.errorMessage?.observe(this, this::errorMessageValueChanged)
        setContentView(binding.root)
        setupMediaPlayer()
    }

    private fun setupMediaPlayer() {
        mediaPlayer = MediaPlayerWrapperImpl().apply {
            setup(this@SearchActivity)
        }
    }

    private fun queryValueChanged(query: String) {
        binding.viewModel?.searchMusic(query)
    }

    private fun musicsValueChanged(musics: List<Music>) {
        displayMusicList(musics)
    }

    private fun errorMessageValueChanged(errorMessage: String) {
        displayErrorMessage(errorMessage)
    }

    private fun displayMusicList(musics: List<Music>) {
        binding.musicListView.apply {
            adapter = MusicAdapter(musics, this@SearchActivity)
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun displayErrorMessage(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onItemMusicClickListener(music: Music) {
        playMusic(music)
    }

    private fun playMusic(music: Music) {
        mediaPlayer.play(music)
    }

}