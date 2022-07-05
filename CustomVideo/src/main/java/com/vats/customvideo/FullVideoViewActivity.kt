package com.vats.customvideo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vats.customvideo.databinding.ActivityFullVideoViewBinding

class FullVideoViewActivity : AppCompatActivity() {
    lateinit var binding : ActivityFullVideoViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullVideoViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}