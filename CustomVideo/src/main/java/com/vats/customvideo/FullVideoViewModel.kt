package com.vats.customvideo

import androidx.lifecycle.ViewModel
import com.vats.customvideo.utils.FullVideoViewState
import com.vats.customvideo.utils.CustomVideoViewUiProperty

class FullVideoViewModel: ViewModel() {

    var activityState = FullVideoViewState.init_state
    var videoUrl :String? = null
    var defaultOrientationState : Int? = null
    var currentDuration = 0
    var customVideoViewUiProperty : CustomVideoViewUiProperty? = null

}