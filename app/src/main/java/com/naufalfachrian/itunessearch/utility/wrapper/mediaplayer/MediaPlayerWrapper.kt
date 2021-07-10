package com.naufalfachrian.itunessearch.utility.wrapper.mediaplayer

import com.naufalfachrian.itunessearch.entity.Music

interface MediaPlayerWrapper {

    interface Callback {

        fun mediaPlayerStarted(music: Music)

        fun mediaPlayerPaused()

        fun mediaPlayerStopped()

        fun mediaPlayerFailed(reason: Throwable)

    }

    fun setup(callback: Callback)

    fun play(music: Music)

    fun pause()

    fun stop()

    fun close()

}