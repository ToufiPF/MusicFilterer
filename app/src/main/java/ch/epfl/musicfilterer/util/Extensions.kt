package ch.epfl.musicfilterer.util

import android.widget.ImageView

object Extensions {

    fun ImageView.setPixelSize(width: Int, height: Int) {
        layoutParams.width = width
        layoutParams.height = height
        requestLayout()
    }
}
