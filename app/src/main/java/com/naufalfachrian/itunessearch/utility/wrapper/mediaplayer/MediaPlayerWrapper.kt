package com.naufalfachrian.itunessearch.utility.wrapper.mediaplayer

import com.naufalfachrian.itunessearch.entity.Music

interface MediaPlayerWrapper {

    interface Callback {

        fun updatePlaybackInformation(music: Music)

        fun mediaPlayerStarted()

        fun mediaPlayerPaused()

        fun mediaPlayerStopped()

        fun mediaPlayerFailed(reason: Throwable)

        fun updateDurationInformation(timePassed: Int, duration: Int)

    }

    fun setup(callback: Callback)

    fun play(music: Music)

    fun start()

    fun pause()

    fun stop()

    fun togglePlayback()

    fun close()

}