package com.santosfabi.catgallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.santosfabi.catgallery.ui.gallery.GalleryFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fcv_main, GalleryFragment())
                .commit()
        }
    }
}