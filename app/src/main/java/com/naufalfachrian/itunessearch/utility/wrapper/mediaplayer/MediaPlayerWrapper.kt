package com.naufalfachrian.itunessearch.utility.wrapper.mediaplayer

import com.naufalfachrian.itunessearch.entity.Music

interface MediaPlayerWrapper {

    interface Callback {
    }

    fun setup(callback: Callback)

    fun play(music: Music)

    fun pause()

    fun stop()

    fun close()

}