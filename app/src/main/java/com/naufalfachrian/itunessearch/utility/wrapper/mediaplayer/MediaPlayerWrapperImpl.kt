package com.naufalfachrian.itunessearch.utility.wrapper.mediaplayer

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import com.naufalfachrian.itunessearch.entity.Music

class MediaPlayerWrapperImpl : MediaPlayerWrapper {

    private val mediaPlayer = MediaPlayer()

    private lateinit var callback: MediaPlayerWrapper.Callback

    override fun setup(callback: MediaPlayerWrapper.Callback) {
        this.callback = callback
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
            callback.mediaPlayerStopped()
        }
        try {
            mediaPlayer.reset()
            mediaPlayer.setDataSource(music.previewPlaybackUrl)
            mediaPlayer.prepareAsync()
            mediaPlayer.start()
            callback.mediaPlayerStarted(music)
            updateDurationInformation()
        } catch (reason: Throwable) {
            callback.mediaPlayerFailed(reason)
        }
    }

    override fun pause() {
        mediaPlayer.pause()
        callback.mediaPlayerPaused()
    }

    override fun stop() {
        mediaPlayer.stop()
        callback.mediaPlayerStopped()
    }

    override fun close() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            callback.mediaPlayerStopped()
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

    private fun updateDurationInformation() {
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                val timePassed = mediaPlayer.currentPosition
                val duration = mediaPlayer.duration
                callback.updateDurationInformation(timePassed, duration)
                handler.postDelayed(this, 100)
            }
        }
        handler.postDelayed(runnable, 100)
    }

}