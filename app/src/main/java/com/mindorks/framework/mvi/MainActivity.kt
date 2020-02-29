package com.mindorks.framework.mvi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mindorks.framework.mvi.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
