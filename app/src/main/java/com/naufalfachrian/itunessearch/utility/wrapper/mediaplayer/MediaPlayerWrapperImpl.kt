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
        }
    }

    override fun play(music: Music) {
        if (mediaPlayer.isPlaying) {
            stop()
        }
        try {
            mediaPlayer.apply {
                setOnPreparedListener { mediaPlayerPrepared() }
                setOnCompletionListener { callback.mediaPlayerStopped() }
                reset()
                setDataSource(music.previewPlaybackUrl)
                prepareAsync()
            }
            callback.updatePlaybackInformation(music)
            updateDurationInformation()
        } catch (reason: Throwable) {
            callback.mediaPlayerFailed(reason)
        }
    }

    override fun start() {
        mediaPlayer.start()
        callback.mediaPlayerStarted()
    }

    override fun pause() {
        mediaPlayer.pause()
        callback.mediaPlayerPaused()
    }

    override fun stop() {
        mediaPlayer.stop()
        callback.mediaPlayerStopped()
    }

    override fun togglePlayback() {
        if (mediaPlayer.isPlaying) {
            pause()
        } else {
            start()
        }
    }

    override fun close() {
        if (mediaPlayer.isPlaying) {
            stop()
        }
        mediaPlayer.release()
    }

    private fun mediaPlayerPrepared() {
        togglePlayback()
    }

    private fun updateDurationInformation() {
        val handler = Handler(Looper.getMainLooper())
        val delayUpdateDurationInformation = 100L // in millis
        val runnable = object : Runnable {
            override fun run() {
                val timePassed = mediaPlayer.currentPosition
                val duration = mediaPlayer.duration
                callback.updateDurationInformation(timePassed, duration)
                handler.postDelayed(this, delayUpdateDurationInformation)
            }
        }
        handler.postDelayed(runnable, delayUpdateDurationInformation)
    }

}