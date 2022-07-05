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
        var path = intent.getStringExtra("videoUrl")
       // var uri = intent.getStringExtra("uri")

        binding.customVideoView.thumbNail().setImageResource(R.drawable.screen_bg)
        binding.customVideoView2.thumbNail().setImageResource(R.drawable.screen_bg)
      // // path = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"

      //  path = "https://storage.googleapis.com/dev-connect-up/videos/user_profile/1651770988-sample-mp4-file-small.mp4?Expires=2952565459\\u0026GoogleAccessId=firebase-adminsdk-g1jf4%40arctic-diode-325214.iam.gserviceaccount.com\\u0026Signature=WbaEFEptoGiAs%2BbGsaNI17mI9eGGX8w4WnCkzjM8LMOO6vS4FyEVUFZq%2B0AeXttftzBTPYRpVE%2Fir74qdsxfmnqDUpUkcMO2adT0XuTLfH1whblf%2BgZ6ynkEXSVaClgWNlHUlYOQgRyHrhCnq%2BNF9ubmATYvaEgIzlRLrCKgFi%2BuVVXCr9vo%2BPdK2rleJvY8uwORlqUzBRpPF9VdMFtMKRSxjxrHj21W5eevku7I6lAJdyD2x50HHLBVX3N%2BKYXIFLWJzvkLa9zyhPajSATmnhMyNOemX1AMYOJS1dp8otaFZUQXUM7YcfunGbRERZ0ZpugFxagSXwDsj80CIoaV7Q%3D%3D"
        binding.customVideoView.setVideoResource(path)
        binding.customVideoView2.setVideoResource(path)

    }
}