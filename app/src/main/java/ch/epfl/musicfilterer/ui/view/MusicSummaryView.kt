package ch.epfl.musicfilterer.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import ch.epfl.musicfilterer.databinding.ViewMusicSummaryBinding

class MusicSummaryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: ViewMusicSummaryBinding =
        ViewMusicSummaryBinding.inflate(LayoutInflater.from(context))

    var musicName: CharSequence?
        get() = binding.musicSummaryName.text
        set(value) {
            binding.musicSummaryName.text = value
        }

    var musicArtist: CharSequence?
        get() = binding.musicSummaryArtist.text
        set(value) {
            binding.musicSummaryArtist.text = value
        }

    var musicAlbum: CharSequence?
        get() = binding.musicSummaryAlbum.text
        set(value) {
            binding.musicSummaryAlbum.text = value
        }

    init {
        addView(binding.root)
    }
}