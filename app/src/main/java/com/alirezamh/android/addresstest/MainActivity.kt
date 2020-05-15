package com.alirezamh.android.addresstest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alirezamh.android.addresstest.ui.main.MainFragment
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val mainFragment by inject<MainFragment>()
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, mainFragment)
                .commitNow()
        }
    }
}
