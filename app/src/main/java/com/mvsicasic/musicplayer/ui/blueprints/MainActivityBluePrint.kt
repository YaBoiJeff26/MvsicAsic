package com.mvsicasic.musicplayer.ui.blueprints

import android.os.Bundle
import android.view.ActionMode
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvsicasic.musicplayer.R
import com.mvsicasic.musicplayer.models.Song
import com.mvsicasic.musicplayer.utils.RecyclerAdapter
import com.mvsicasic.musicplayer.utils.SongProvider
import kotlinx.android.synthetic.main.content_main.recyclerView

abstract class MainActivityBluePrint : AppCompatActivity(), RecyclerAdapter.SongsSelected, RecyclerAdapter.SongClicked {
    private var actionMode: ActionMode? = null
    private var songAdapter: RecyclerAdapter? = null
    private var deviceMusic = mutableListOf<Song>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        songAdapter = RecyclerAdapter()
        setViews()
    }

    private fun setViews() {

        songAdapter?.setSongsSelected(this)
        songAdapter?.setOnSongClicked(this)
        recyclerView?.apply {
            adapter = songAdapter
            layoutManager = LinearLayoutManager(this@MainActivityBluePrint)
            hasFixedSize()
        }

    }

    fun getMusic() {
        deviceMusic.addAll(SongProvider.getAllDeviceSongs(this))
        songAdapter?.addSongs(deviceMusic)
    }

    override fun onSelectSongs(selectedSongs: MutableList<Song>) {
        if (selectedSongs.isEmpty()) {
            actionMode?.finish()
            songAdapter?.removeSelection()
        }
    }
}