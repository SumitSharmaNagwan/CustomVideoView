package com.vats.customvideoview

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.vats.customvideoview.databinding.ActivityMainBinding

private const val PERMISSIONS_REQUEST_CODE_GALLERY = 54
private val PERMISSIONS_STORAGE_REQUIRED =
    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

class MainActivity : AppCompatActivity() {
    lateinit var binding  : ActivityMainBinding


    private val startActivityForResultForImage: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val uri = activityResult.data?.data
            if (activityResult.resultCode != RESULT_OK) {
                return@registerForActivityResult
            }
            uri?.let {
                val originPath: String? = getRealPath(this, uri)
                originPath?.let { path ->
                    val intent = Intent(this, PlayVideoActivity::class.java)
                    intent.putExtra("videoUrl", path)
                    intent.putExtra("uri",it)
                    Log.d("path", ":$path ")
                    startActivity(intent)
                }
            }
        }






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.selectVideo.setOnClickListener {
            val path = ":/storage/emulated/0/Compose/Jetpack Compose basics code-along.mp4"
            checkPermission()

        }
    }

    private fun checkPermission(){

        if (hasStoragePermissions(this)) {
            selectImageFromGallery()
        } else {
            requestPermissions(
                PERMISSIONS_STORAGE_REQUIRED,
                PERMISSIONS_REQUEST_CODE_GALLERY
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE_GALLERY -> {
                if (PackageManager.PERMISSION_GRANTED == grantResults.firstOrNull()) {
                    selectImageFromGallery()
                }
            }
        }

    }
    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "video/*"
        startActivityForResultForImage.launch(intent)

    }
}

fun getRealPath(context: Context, contentUri: Uri): String? {
    var cursor: Cursor? = null
    return try {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        cursor = context.contentResolver.query(contentUri, proj, null, null, null)

        cursor?.let {
            val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(columnIndex)
        }

    } finally {
        cursor?.close()
    }
}
fun hasStoragePermissions(context: Context) = PERMISSIONS_STORAGE_REQUIRED.all {
    ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
}