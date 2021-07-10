package com.naufalfachrian.itunessearch.feature.search

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naufalfachrian.itunessearch.R
import com.naufalfachrian.itunessearch.adapter.MusicAdapter
import com.naufalfachrian.itunessearch.databinding.SearchActivityBinding
import com.naufalfachrian.itunessearch.entity.Music
import com.naufalfachrian.itunessearch.utility.extension.setImageFromUrlString
import com.naufalfachrian.itunessearch.utility.wrapper.mediaplayer.MediaPlayerWrapper
import com.naufalfachrian.itunessearch.utility.wrapper.mediaplayer.MediaPlayerWrapperImpl

class SearchActivity : AppCompatActivity(), MusicAdapter.Callback, MediaPlayerWrapper.Callback {

    private lateinit var binding: SearchActivityBinding

    private lateinit var mediaPlayer: MediaPlayerWrapper

    private lateinit var playerSheetController: BottomSheetBehavior<ConstraintLayout>

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
        setupPlayerSheet()
        setupPlaybackButton()
    }

    override fun onDestroy() {
        mediaPlayer.close()
        super.onDestroy()
    }

    private fun setupMediaPlayer() {
        mediaPlayer = MediaPlayerWrapperImpl().apply {
            setup(this@SearchActivity)
        }
    }

    private fun setupPlayerSheet() {
        playerSheetController = BottomSheetBehavior.from(binding.playerSheet.root as ConstraintLayout).apply {
            isDraggable = false
        }
    }

    private fun setupPlaybackButton() {
        binding.playerSheet.playPauseButton.setOnClickListener { mediaPlayer.togglePlayback() }
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
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
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

    override fun updatePlaybackInformation(music: Music) {
        binding.playerSheet.music = music
        binding.playerSheet.playerAlbumArtView.setImageFromUrlString(music.albumArtUrl)
    }

    override fun mediaPlayerStarted() {
        playerSheetController.state = BottomSheetBehavior.STATE_EXPANDED
        binding.playerSheet.playPauseButton.setImageResource(R.drawable.ic_baseline_pause_24)
    }

    override fun mediaPlayerPaused() {
        playerSheetController.state = BottomSheetBehavior.STATE_EXPANDED
        binding.playerSheet.playPauseButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
    }

    override fun mediaPlayerFailed(reason: Throwable) {
        playerSheetController.state = BottomSheetBehavior.STATE_HIDDEN
        Toast.makeText(this, reason.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    override fun mediaPlayerStopped() {
        playerSheetController.state = BottomSheetBehavior.STATE_HIDDEN
        binding.playerSheet.playPauseButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
    }

    override fun updateDurationInformation(timePassed: Int, duration: Int) {
        binding.playerSheet.durationBar.apply {
            max = duration
            progress = timePassed
        }
    }

}