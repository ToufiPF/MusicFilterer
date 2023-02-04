package ch.epfl.musicfilterer.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Music(
    val name: String,
    val artist: String,
    val album: String,
    val filepath: String,
) : Parcelable
