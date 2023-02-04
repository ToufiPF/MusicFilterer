package ch.epfl.musicfilterer.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.epfl.musicfilterer.R
import ch.epfl.musicfilterer.data.Music
import ch.epfl.musicfilterer.ui.adapter.PlaylistAdapter

class PlaylistFragment : Fragment(R.layout.fragment_playlist) {

    private lateinit var adapter: PlaylistAdapter
    private lateinit var recycler: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PlaylistAdapter()
        recycler = view.findViewById(R.id.playlist_recycler)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        adapter.resetPlaylist(
            arrayListOf(
                Music("NameA", "Artistide", "1", "/data/music/a.mp3"),
                Music("NameB", "Artistide", "1", "/data/music/b.mp3"),
                Music("NameC", "Artistide", "1", "/data/music/c.mp3"),
                Music("NameD", "Artistide", "1", "/data/music/d.mp3"),
                Music("NameE", "Artistide", "2", "/data/music/e.mp3"),
                Music("NameF", "Artistide", "3", "/data/music/f.mp3"),
                Music("NameG", "Artistide", "3", "/data/music/g.mp3"),
            )
        )
    }
}