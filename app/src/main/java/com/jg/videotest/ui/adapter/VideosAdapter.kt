package com.jg.videotest.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jg.videotest.databinding.RowVideoBinding
import com.jg.videotest.model.Video

class VideosAdapter(
    private val videos: List<Video>,
    private val videoClickListener: VideoClickListener
): RecyclerView.Adapter<VideosAdapter.VideoViewHolder>() {

    private var _rowVideoBinding: RowVideoBinding? = null
    private val rowVideoBinding get() = _rowVideoBinding!!


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        _rowVideoBinding = RowVideoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VideoViewHolder(rowVideoBinding, videoClickListener)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videos[position])
    }

    override fun getItemCount(): Int = videos.size



    //region ViewHolder
    inner class VideoViewHolder(
        private val binding: RowVideoBinding,
        private val listener: VideoClickListener
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        private lateinit var video: Video

        fun bind(item: Video) {
            video = item
            Glide.with(binding.root)
                .load(video.thumb)
                .into(binding.videoThumbnail)
            binding.root.setOnClickListener {
                this.onClick(it)
            }
        }

        override fun onClick(v: View?) {
            listener.onVideoClick(video)
        }

    }
    //endregion

    interface VideoClickListener {
        fun onVideoClick(video: Video)
    }
}