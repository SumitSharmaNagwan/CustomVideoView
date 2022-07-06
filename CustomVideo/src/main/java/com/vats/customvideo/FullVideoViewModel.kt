package com.vats.customvideo

import android.content.pm.ActivityInfo
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vats.customvideo.utils.FullVideoViewState

class FullVideoViewModel: ViewModel() {

    var activityState = FullVideoViewState.init_state
    var videoUrl :String? = null
    var defaultOrientationState : Int? = null
    var currentDuration = 0

}