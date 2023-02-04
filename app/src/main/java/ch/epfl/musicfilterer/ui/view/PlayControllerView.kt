package ch.epfl.musicfilterer.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import ch.epfl.musicfilterer.R
import ch.epfl.musicfilterer.databinding.ViewPlayControllerBinding
import ch.epfl.musicfilterer.util.Extensions.setPixelSize

/**
 * A view with 3 buttons: previous, play/pause, next
 */
class PlayControllerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    interface Listener {
        /**
         * Callback for when user clicked on the 'previous' button
         */
        fun onPrevious()

        /**
         * Callback for when user clicked on the 'play' button (it was paused beforehand)
         */
        fun onResume()

        /**
         * Callback for when user clicked on the 'pause' button (it was resumed beforehand)
         */
        fun onPause()

        /**
         * Callback for when user clicked on the 'next' button
         */
        fun onNext()
    }


    private val binding: ViewPlayControllerBinding =
        ViewPlayControllerBinding.inflate(LayoutInflater.from(context))

    private val listeners = HashSet<Listener>()

    var paused: Boolean = true
        set(value) {
            if (field != value) {
                field = value

                if (value) {
                    binding.playControllerPlayPause.setImageResource(R.drawable.play)
                    listeners.forEach(Listener::onPause)
                } else {
                    binding.playControllerPlayPause.setImageResource(R.drawable.pause)
                    listeners.forEach(Listener::onResume)
                }
            }
        }

    val playing: Boolean get() = !paused

    fun addListener(listener: Listener) {
        listeners.add(listener)
    }

    fun removeListener(listener: Listener) {
        listeners.remove(listener)
    }

    var iconSizePx: Int
        get() = binding.playControllerPrevious.height
        set(px) {
            binding.apply {
                playControllerPrevious.setPixelSize(px, px)
                playControllerPlayPause.setPixelSize(px, px)
                playControllerNext.setPixelSize(px, px)
            }
        }

    init {
        addView(binding.root)

        binding.apply {
            context.theme.obtainStyledAttributes(attrs, R.styleable.PlayControllerView, 0, 0)
                .let { array ->
                    try {
                        val size = array.getDimensionPixelSize(
                            R.styleable.PlayControllerView_iconSize,
                            context.resources.getDimensionPixelSize(R.dimen.play_controller_default_icon_size),
                        )
                        playControllerPrevious.setPixelSize(size, size)
                        playControllerPlayPause.setPixelSize(size, size)
                        playControllerNext.setPixelSize(size, size)
                    } finally {
                        array.recycle()
                    }
                }

            playControllerPrevious.setOnClickListener { listeners.forEach(Listener::onPrevious) }
            playControllerNext.setOnClickListener { listeners.forEach(Listener::onNext) }
            playControllerPlayPause.setOnClickListener { paused = !paused }
        }
    }
}
