package com.naufalfachrian.itunessearch.utility.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatSeekBar

class UnmovableSeekBar(context: Context, attributeSet: AttributeSet) : AppCompatSeekBar(context, attributeSet) {

    override fun performClick(): Boolean {
        super.performClick()
        return false
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        performClick()
        return false
    }

}