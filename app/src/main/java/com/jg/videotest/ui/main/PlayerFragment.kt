package com.jg.videotest.ui.main

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.jg.videotest.databinding.FragmentPlayerBinding
import com.jg.videotest.model.Video
import com.jg.videotest.ui.base.BaseFragment

class PlayerFragment: BaseFragment() {

    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!
    private lateinit var video: Video
    private lateinit var player: ExoPlayer
    

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).apply {
            supportActionBar?.hide()
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initializeState(savedInstanceState: Bundle?) {
        video = arguments?.get("video") as Video
        Log.d("Video", video.name)
    }

    override fun initializeViews(savedInstanceState: Bundle?) {
        player = ExoPlayer.Builder(requireContext()).build()
        binding.pvMainPlayer.player = player
    }

    override fun initializeContents(savedInstanceState: Bundle?) {
        player.apply {
            val mediaItem = MediaItem.fromUri(video.videoUrl)
            setMediaItem(mediaItem)
            prepare()
            play()
        }
    }

    override fun onPause() {
        super.onPause()
        player.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}
