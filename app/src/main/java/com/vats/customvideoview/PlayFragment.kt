package com.vats.customvideoview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vats.customvideoview.databinding.FragmentPlayBinding

class PlayFragment : Fragment() {

    lateinit var binding : FragmentPlayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPlayBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    fun setUp(path:String?){
        binding.customVideoView.setUpVideo(path)
    }

}