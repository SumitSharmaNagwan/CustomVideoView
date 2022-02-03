package com.vats.customvideo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.vats.customvideo.databinding.VideoViewLayoutBinding
import com.vats.customvideo.utils.IVideoViewActionListener
import kotlinx.coroutines.*

class ViewFragment : Fragment(), IVideoViewActionListener {
    private lateinit var timeCounterJob: Job
    var isPlay = false
    lateinit var binding: VideoViewLayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = VideoViewLayoutBinding.inflate(LayoutInflater.from(context), null, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        setObserver()
    }

    private fun setObserver() {

        binding.videoView.setOnCompletionListener {
            binding.playButtonPanel.visibility = View.VISIBLE
            binding.bottomControlPanel.visibility = View.VISIBLE
            binding.playButton.visibility = View.GONE
            binding.pauseButton.visibility = View.GONE
            binding.playAgainButton.visibility = View.VISIBLE
        }

        binding.seekbarVideo.setOnSeekBarChangeListener(
            @SuppressLint("AppCompatCustomView")
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, p1: Int, p2: Boolean) {
                    Log.d("TAG", "onProgressChanged: ")
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    seekBar?.let {
                        val currentTimePercentage = seekBar.progress
                        val totalTime = binding.videoView.duration
                        //  val currentTime = (currentTimePercentage * currentTimePercentage)/100
                        updateSeekBar(currentTimePercentage, totalTime)
                        binding.videoView.seekTo(currentTimePercentage)
                    }
                }
            })
    }

    private fun setListener() {

        binding.videoView.setOnPreparedListener {
            it?.let {
                val maxDuration = it.duration
                binding.seekbarVideo.max = maxDuration
                updateSeekBar(0, maxDuration)

            }
        }

        binding.playAgainButton.setOnClickListener {
            binding.playButton.visibility = View.GONE
            binding.playAgainButton.visibility = View.GONE
            binding.pauseButton.visibility = View.VISIBLE
            binding.videoView.start()

        }
        binding.viewForHideControl.setOnClickListener {
            if (binding.playButtonPanel.visibility == View.GONE) {
                binding.playButtonPanel.visibility = View.VISIBLE
                binding.bottomControlPanel.visibility = View.VISIBLE
            } else {
                binding.playButtonPanel.visibility = View.GONE
                binding.bottomControlPanel.visibility = View.GONE
            }
        }

        binding.playButton.setOnClickListener {
            binding.playButton.visibility = View.GONE
            binding.pauseButton.visibility = View.VISIBLE
            val currentTime = binding.seekbarVideo.progress
            binding.videoView.start()
            // binding.videoView.seekTo(currentTime)
            isPlay = true
            timeCounter()

        }
        binding.pauseButton.setOnClickListener {
            binding.playButton.visibility = View.VISIBLE
            binding.pauseButton.visibility = View.GONE
            binding.videoView.pause()
            isPlay = false
            if (this::timeCounterJob.isInitialized) {
                if (timeCounterJob.isActive) {
                    timeCounterJob.cancel()
                }
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun timeCounter() {


        timeCounterJob = CoroutineScope(Dispatchers.IO).launch {
            while (isPlay) {
                delay(300)
                launch(Dispatchers.Main) {

                    val currentTime = binding.videoView.currentPosition
                    val totalTime = binding.videoView.duration
                    updateSeekBar(currentTime, totalTime)
                    binding.seekbarVideo.max = totalTime
                    binding.seekbarVideo.progress = currentTime
                    Log.d("TAG", "timeCounter:  $currentTime    $totalTime")
                }
            }
        }
    }

    private fun updateSeekBar(currentTime: Int, totalTime: Int) {
        val currentTimeInSec = currentTime / 1000
        val currentMin = currentTimeInSec / 60
        val currentSec = currentTimeInSec % 60
        val totalTimeInSec = totalTime / 1000
        val totalMin = totalTimeInSec / 60
        val endSec = totalTimeInSec % 60
        binding.videoTime.text = "$currentMin:$currentSec / $totalMin:$endSec"

    }

    override fun onDestroy() {
        super.onDestroy()
        if (this.timeCounterJob.isActive) {
            timeCounterJob.cancel()
        }
    }

    override fun OnSetVideoSource(path: String?) {
        if (path != null && path.isNotEmpty()) {
            binding.videoView.setVideoPath(path)
            val currentTime = binding.videoView.currentPosition
            val totalTime = binding.videoView.duration
            updateSeekBar(currentTime, totalTime)
        }
    }
}