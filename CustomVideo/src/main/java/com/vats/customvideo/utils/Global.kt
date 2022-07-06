package com.vats.customvideo.utils

import android.media.MediaPlayer
import com.vats.customvideo.CustomVideoView

internal var previousVideoControlList = HashMap<Long,CustomVideoView>()
// internal var mediaPlayer: MediaPlayer? = null

internal var currentDurationForSmallView :Int? = null
enum class FullVideoViewState{
    init_state,
    destroy_state
}