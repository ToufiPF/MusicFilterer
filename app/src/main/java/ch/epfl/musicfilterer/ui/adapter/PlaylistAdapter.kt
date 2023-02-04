package ch.epfl.musicfilterer.ui.adapter

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View.DragShadowBuilder
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import ch.epfl.musicfilterer.data.Music
import ch.epfl.musicfilterer.databinding.RecyclerItemPlaylistBinding
import java.util.*

class PlaylistAdapter : RecyclerView.Adapter<PlaylistAdapter.ViewHolder>() {

    private data class DragInfo(
        val oldPosition: Int
    )

    inner class ViewHolder(
        private val binding: RecyclerItemPlaylistBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                // Drag/Drop handle listens for long clicks & start drag&drop operation from the ViewHolder root
                playlistItemDragDrop.isLongClickable = true
                playlistItemDragDrop.setOnLongClickListener {
                    onDragHandleClick()
                }

                root.setOnDragListener { _, dragEvent ->
                    onDragEvent(dragEvent)
                }
            }
        }

        fun bind(music: Music) {
            binding.apply {
                playlistItemMusicName.text = music.name
                playlistItemArtistName.text = music.artist
            }
        }

        private fun onDragHandleClick(): Boolean {
            val pos = this@ViewHolder.adapterPosition
            if (pos == NO_POSITION) return false

            val clipData = ClipData.newPlainText(
                this::class.simpleName!!,
                playlist[pos].filepath,
            )
            val shadow = DragShadowBuilder(binding.root)
            val localState = DragInfo(pos)

            ViewCompat.startDragAndDrop(binding.root, clipData, shadow, localState, 0)
            return true
        }

        private fun onDragEvent(dragEvent: DragEvent): Boolean = when (dragEvent.action) {
            DragEvent.ACTION_DRAG_STARTED ->
                dragEvent.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
                        && dragEvent.localState != null

            DragEvent.ACTION_DROP -> {
                val root = binding.root
                val dragInfo = dragEvent.localState as? DragInfo
                val pos = this@ViewHolder.adapterPosition
                if (dragInfo == null || pos == NO_POSITION) false
                else {
                    val newPosition =
                        pos + if (dragInfo.oldPosition < pos && dragEvent.y < root.height / 2) -1
                        else if (dragInfo.oldPosition > pos && dragEvent.y > root.height / 2) 1
                        else 0
                    moveMusic(dragInfo.oldPosition, newPosition)
                    true
                }
            }
            else -> false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RecyclerItemPlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(playlist[position])
    }

    override fun getItemCount(): Int = playlist.size

    private var playlist = LinkedList<Music>()

    @SuppressLint("NotifyDataSetChanged")
    fun resetPlaylist(list: List<Music>) {
        playlist = LinkedList(list)
        notifyDataSetChanged()
    }

    fun moveMusic(oldPosition: Int, newPosition: Int) {
        val music = playlist.removeAt(oldPosition)

        if (oldPosition < newPosition) playlist.add(newPosition - 1, music)
        else playlist.add(newPosition, music)

        notifyItemMoved(oldPosition, newPosition)
    }
}
