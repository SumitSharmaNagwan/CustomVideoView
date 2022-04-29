package com.vats.customvideo

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.media.MediaPlayer
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.annotation.Dimension.DP
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.setPadding
import androidx.fragment.app.FragmentManager
import com.vats.customvideo.databinding.VideoViewLayoutBinding
import com.vats.customvideo.utils.formatVideoTime
import kotlinx.coroutines.*

class CustomVideoView(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {
    private var videoUrl: String? = null
    private var progressTintColor: Int = Color.RED
    private var progressBackgroundColor = Color.WHITE
    private var thumbTintColor = Color.RED
    private var scaleType: ScaleType = ScaleType.centerInside
    private var timeLabelColor: Int = Color.WHITE
    private var timeLabelTextSize = 12
    private var timeCounterJob: Job? = null
    private var playIcon = R.drawable.ic_baseline_play_arrow_24
    private var pauseIcon = R.drawable.ic_baseline_pause_24
    private var replayIcon = R.drawable.outline_replay
    private var iconHeight =  48
    private var iconWidth =  48
    private var iconPadding = 8
    var isPlay = false
    var mediaPlayer: MediaPlayer? = null
    private var binding: VideoViewLayoutBinding =
        VideoViewLayoutBinding.inflate(LayoutInflater.from(context), null, false)

    init {
        this.addView(binding.root)
        binding.root.layoutParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        setObserver()
        setListener()
        setInitValue(attributeSet)
    }

    fun setVideoResource(path: String?) {
        if (path != null && path.isNotEmpty()) {
            binding.videoView.setVideoPath(path)
            val currentTime = binding.videoView.currentPosition
            val totalTime = binding.videoView.duration
            updateSeekBar(currentTime, totalTime)
        }

    }

    private fun setListener() {

        binding.playAgainButton.setOnClickListener {
            binding.playButton.visibility = View.GONE
            binding.playAgainButton.visibility = View.GONE
            binding.pauseButton.visibility = View.VISIBLE
            binding.videoView.start()
            timeCounter()

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

            if (timeCounterJob != null) {
                if (timeCounterJob!!.isActive) {
                    timeCounterJob!!.cancel()
                    timeCounterJob = null
                }
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun timeCounter() {
        isPlay = true
        if (timeCounterJob != null) {
            if (timeCounterJob!!.isActive) {
                timeCounterJob!!.cancel()
                timeCounterJob = null
            }
        }

        timeCounterJob = CoroutineScope(Dispatchers.IO).launch {
            while (isPlay) {
                delay(300)
                launch(Dispatchers.Main) {

                    isPlay = binding.videoView.isPlaying
                    binding.let {
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
    }

    private fun scaleTypeCenterInside(mediaPlayer: MediaPlayer) {
        val videoRatio = mediaPlayer.videoWidth / mediaPlayer.videoHeight.toFloat()
        if (videoRatio <= 1) {
            binding.videoView.scaleX = videoRatio
        }
    }

    private fun setObserver() {

        binding.videoView.setOnPreparedListener {
            it?.let {
                mediaPlayer = it
                val maxDuration = it.duration
                binding.seekbarVideo.max = maxDuration
                updateSeekBar(0, maxDuration)
                setUpScaleType()
            }
        }

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

    private fun updateSeekBar(currentTime: Int, totalTime: Int) {
        val currentTimeString = formatVideoTime(currentTime)
        val totalTimeString = formatVideoTime(totalTime)
        binding.videoTime.text = "$currentTimeString / $totalTimeString"
    }

    private fun setUpScaleType() {
        mediaPlayer?.let {
            when (scaleType) {
                ScaleType.centerInside -> {
                    scaleTypeCenterInside(it)
                }
                ScaleType.centerCrop -> {

                }
            }
        }
    }

    private fun updateUi() {
        setVideoResource(videoUrl)
        binding.videoTime.setTextColor(timeLabelColor)
        binding.videoTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, timeLabelTextSize.toFloat())

        binding.playButton.setImageResource(playIcon)
        binding.pauseButton.setImageResource(pauseIcon)
        binding.playAgainButton.setImageResource(replayIcon)
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.videoViewRoot)


        constraintSet.constrainHeight(binding.playButtonPanel.id,iconHeight)
        constraintSet.constrainWidth(binding.playButtonPanel.id,iconWidth)
        // binding.playButtonPanel.layoutParams = LayoutParams(iconWidth, iconHeight)
        binding.playButton.setPadding(iconPadding)
        binding.pauseButton.setPadding(iconPadding)
        binding.playAgainButton.setPadding(iconPadding)

        constraintSet.applyTo(binding.videoViewRoot)
        // binding.seekbarVideo.setBackgroundColor(progressBackgroundColor)
        val progressColorFilter = PorterDuffColorFilter(progressTintColor, PorterDuff.Mode.SRC_IN)
        binding.seekbarVideo.progressDrawable.colorFilter = progressColorFilter
        val thumbColorFilter = PorterDuffColorFilter(thumbTintColor, PorterDuff.Mode.SRC_IN)
        binding.seekbarVideo.thumb.colorFilter = thumbColorFilter
        val progressBackgroundColorFilter =
            PorterDuffColorFilter(progressBackgroundColor, PorterDuff.Mode.SRC_IN)
        binding.seekbarVideo.progressDrawable
    }

    @SuppressLint("Recycle")
    private fun setInitValue(attributeSet: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CustomVideoView)
        videoUrl = typedArray.getString(R.styleable.CustomVideoView_video_url)
        progressTintColor =
            typedArray.getColor(R.styleable.CustomVideoView_progress_tint_color, progressTintColor)
        progressBackgroundColor = typedArray.getColor(
            R.styleable.CustomVideoView_progress_background_color,
            progressBackgroundColor
        )
        progressBackgroundColor = typedArray.getColor(
            R.styleable.CustomVideoView_progress_background_color,
            progressBackgroundColor
        )
        thumbTintColor =
            typedArray.getColor(R.styleable.CustomVideoView_thumb_tint_color, thumbTintColor)
        scaleType = typedArray.getEnum(R.styleable.CustomVideoView_scale_type, scaleType)
        timeLabelColor =
            typedArray.getColor(R.styleable.CustomVideoView_time_label_color, timeLabelColor)
        timeLabelTextSize = typedArray.getDimensionPixelSize(
            R.styleable.CustomVideoView_time_label_size,
            timeLabelTextSize
        )
        playIcon = typedArray.getResourceId(R.styleable.CustomVideoView_play_icon,playIcon)
        pauseIcon = typedArray.getResourceId(R.styleable.CustomVideoView_pause_icon,pauseIcon)
        replayIcon = typedArray.getResourceId(R.styleable.CustomVideoView_replay_icon,replayIcon)
        iconHeight = typedArray.getDimensionPixelSize(R.styleable.CustomVideoView_icon_height,iconHeight)
        iconWidth = typedArray.getDimensionPixelSize(R.styleable.CustomVideoView_icon_width,iconWidth)
        iconPadding = typedArray.getDimensionPixelSize(R.styleable.CustomVideoView_icon_padding,iconPadding)
        updateUi()
    }

    inline fun <reified T : Enum<T>> TypedArray.getEnum(index: Int, default: T) =
        getInt(index, -1).let {
            if (it >= 0) enumValues<T>()[it] else default
        }
}

enum class ScaleType {
    centerCrop,
    centerInside
}