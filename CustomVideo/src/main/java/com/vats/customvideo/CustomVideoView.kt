package com.vats.customvideo

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import com.vats.customvideo.databinding.ContainerViewBinding
import com.vats.customvideo.utils.IVideoViewActionListener
import kotlinx.coroutines.*

class CustomVideoView(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet)  {

    lateinit var fragment: ViewFragment

    private val binding = ContainerViewBinding.inflate(LayoutInflater.from(context), null, false)

    init {
        this.addView(binding.root)
        binding.root.layoutParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        if(context is FragmentActivity){
           val fragment = context.supportFragmentManager.findFragmentById(R.id.customVideoViewFragmentId)
            if (fragment is ViewFragment){
                this.fragment = fragment
            }
        }

    }

    fun setUpVideo(path: String?) {


        fragment?.let {
            if (it is IVideoViewActionListener){
                it.OnSetVideoSource(path)
            }
        }
        if (path!= null && path.isNotEmpty()){

         //   binding.videoView.setVideoPath(path)
         //   val currentTime = binding.videoView.currentPosition
         //   val totalTime = binding.videoView.duration
         //   updateSeekBar(currentTime, totalTime)
        }
    }



}