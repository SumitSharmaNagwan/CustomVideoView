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
        val fragment = supportFragmentManager.findFragmentById(R.id.testFragment)
        fragment?.let {
            if (fragment is PlayFragment){
                fragment.setUp(path)
            }
        }
    }
}