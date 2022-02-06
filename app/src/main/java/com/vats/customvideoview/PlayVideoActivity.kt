package com.vats.customvideoview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vats.customvideoview.databinding.ActivityPlayVideoBinding

class PlayVideoActivity : AppCompatActivity() {
    lateinit var binding : ActivityPlayVideoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val path = intent.getStringExtra("videoUrl")
        binding.customVideoView.setVideoResource(path)

    }
}