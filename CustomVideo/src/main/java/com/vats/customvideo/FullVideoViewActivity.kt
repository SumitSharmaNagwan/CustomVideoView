package com.vats.customvideo

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.vats.customvideo.databinding.ActivityFullVideoViewBinding
import com.vats.customvideo.utils.FullVideoViewState
import com.vats.customvideo.utils.CustomVideoViewUiProperty
import com.vats.customvideo.utils.currentDurationForSmallView

class FullVideoViewActivity : AppCompatActivity() {
     lateinit var fullVideoViewModel: FullVideoViewModel
    lateinit var binding : ActivityFullVideoViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityFullVideoViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        fullVideoViewModel = ViewModelProvider(this).get(FullVideoViewModel::class.java)
        if (fullVideoViewModel.activityState == FullVideoViewState.init_state){
             intent.getSerializableExtra("data").let {
                 if (it is CustomVideoViewUiProperty){
                     fullVideoViewModel.customVideoViewUiProperty = it
                     binding.customVideoView.setUiProperty(fullVideoViewModel.customVideoViewUiProperty!!)
                 }
             }
            fullVideoViewModel.currentDuration = intent.getIntExtra("currentDuration",0)
            fullVideoViewModel.videoUrl = intent.getStringExtra("videoUrl")
            fullVideoViewModel.defaultOrientationState = intent.getIntExtra("orientation",ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
            binding.customVideoView.setVideoPrepareListener {
                binding.customVideoView.seekTo(fullVideoViewModel.currentDuration)
                binding.customVideoView.playVideo()
            }

        }else{
           setResult()
        }
        binding.customVideoView.setViewFullMode {
            movePreviousScreen()
        }
        binding.customVideoView.setVideoResource(fullVideoViewModel.videoUrl)

    }

    override fun setRequestedOrientation(requestedOrientation: Int) {
        super.setRequestedOrientation(requestedOrientation)

    }
    private fun setResult(){
        currentDurationForSmallView = fullVideoViewModel.currentDuration
      //  val intent = Intent()
      //  intent.putExtra("currentDuration",currentDuration)
      //  setResult(333,intent)
        finish()
    }

    private fun movePreviousScreen(){
        fullVideoViewModel.activityState = FullVideoViewState.destroy_state
        fullVideoViewModel.currentDuration = binding.customVideoView.getCurrentDuration()
        if (fullVideoViewModel.defaultOrientationState!! == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setResult()
        }else{
            requestedOrientation = fullVideoViewModel.defaultOrientationState!!
        }
    }

    override fun onBackPressed() {
        movePreviousScreen()

    }
}