package com.shurik.memwor_22.content.module_adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shurik.memwor_22.R

class MediaPagerAdapter(private val images: MutableList<String>, private val videos: MutableList<String>) :
    RecyclerView.Adapter<MediaPagerAdapter.MediaViewHolder>() {

    inner class MediaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val videoView: VideoView = itemView.findViewById(R.id.videoView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        return MediaViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.media_page_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        if (position < images.size) {
            holder.imageView.visibility = View.VISIBLE
            holder.videoView.visibility = View.GONE
            Glide.with(holder.itemView.context)
                .load(images[position])
                .into(holder.imageView)

            // Добавление обработчиков событий нажатия и отпускания для imageView
            holder.imageView.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> scaleViewUp(holder.imageView)
                    MotionEvent.ACTION_UP,
                    MotionEvent.ACTION_CANCEL -> scaleViewDown(holder.imageView)
                }
                true
            }
        } else {
            holder.imageView.visibility = View.GONE
            holder.videoView.visibility = View.VISIBLE
            val videoPosition = position - images.size
            val videoUri = Uri.parse(videos[videoPosition])
            holder.videoView.setVideoURI(videoUri)
            holder.videoView.setOnPreparedListener { mp -> mp.isLooping = true }
            holder.videoView.setOnCompletionListener { holder.videoView.start() }
            holder.videoView.requestFocus()
            holder.videoView.start()

            // Добавление обработчиков событий нажатия и отпускания для videoView
            holder.videoView.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> scaleViewUp(holder.videoView)
                    MotionEvent.ACTION_UP,
                    MotionEvent.ACTION_CANCEL -> scaleViewDown(holder.videoView)
                }
                true
            }
        }
    }


    private fun scaleViewUp(view: View) {
        view.animate().scaleX(1.5f).scaleY(1.5f).setDuration(200).start()
    }

    private fun scaleViewDown(view: View) {
        view.animate().scaleX(1f).scaleY(1f).setDuration(200).start()
    }

    override fun getItemCount(): Int {
        return images.size + videos.size
    }
}
