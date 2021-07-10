package com.naufalfachrian.itunessearch.utility.wrapper.mediaplayer

import android.media.AudioAttributes
import android.media.MediaPlayer
import com.naufalfachrian.itunessearch.entity.Music

class MediaPlayerWrapperImpl : MediaPlayerWrapper {

    private val mediaPlayer = MediaPlayer()

    override fun setup(callback: MediaPlayerWrapper.Callback) {
        mediaPlayer.apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            setOnPreparedListener { mediaPlayerPrepared() }
        }
    }

    override fun play(music: Music) {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.reset()
        mediaPlayer.setDataSource(music.previewPlaybackUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.start()
    }

    override fun pause() {
        mediaPlayer.pause()
    }

    override fun stop() {
        mediaPlayer.stop()
    }

    override fun close() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release()
    }

    private fun mediaPlayerPrepared() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        } else {
            mediaPlayer.start()
        }
    }

}