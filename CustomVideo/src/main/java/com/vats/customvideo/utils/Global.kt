package com.vats.customvideo.utils

import android.graphics.Color
import android.media.MediaPlayer
import androidx.annotation.DrawableRes
import com.vats.customvideo.CustomVideoView
import com.vats.customvideo.R
import com.vats.customvideo.ScaleType
import kotlinx.coroutines.Job
import java.io.Serializable

internal var previousVideoControlList = HashMap<Long,CustomVideoView>()
// internal var mediaPlayer: MediaPlayer? = null

internal var currentDurationForSmallView :Int? = null
enum class FullVideoViewState{
    init_state,
    destroy_state
}

data class CustomVideoViewUiProperty(
    val progressTintColor: Int = Color.RED,
    val progressBackgroundColor: Int =  Color.WHITE,
    val thumbTintColor:Int = Color.RED,
    val timeLabelColor: Int = Color.WHITE,
    val timeLabelTextSize : Int = 12,
    @DrawableRes
    val playIcon: Int = R.drawable.ic_baseline_play_arrow_24,
    @DrawableRes
    val pauseIcon : Int = R.drawable.ic_baseline_pause_24,
    @DrawableRes
    val replayIcon : Int = R.drawable.outline_replay,
    val iconHeight : Int =48,
    val iconPadding : Int = 48,
    val scaleType : ScaleType = ScaleType.centerCrop
):Serializable
