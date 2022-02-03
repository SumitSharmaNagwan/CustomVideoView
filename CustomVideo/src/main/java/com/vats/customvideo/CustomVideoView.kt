package com.vats.customvideo

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.vats.customvideo.databinding.ContainerViewBinding
import kotlinx.coroutines.*

class CustomVideoView(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet)  {


    private val binding = ContainerViewBinding.inflate(LayoutInflater.from(context), null, false)

    init {
        this.addView(binding.root)
        binding.root.layoutParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    fun setUpVideo(path: String?) {
        binding.lifecycleOwner

        if (path!= null && path.isNotEmpty()){

         //   binding.videoView.setVideoPath(path)
         //   val currentTime = binding.videoView.currentPosition
         //   val totalTime = binding.videoView.duration
         //   updateSeekBar(currentTime, totalTime)
        }
    }



}