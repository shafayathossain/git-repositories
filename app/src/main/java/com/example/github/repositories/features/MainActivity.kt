package com.example.github.repositories.features

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.github.repositories.base.BaseFragmentCommunicator
import com.example.github.repositories.data.local.LocalDataStore
import com.example.github.repositories.features.main.MainFragment

class MainActivity : AppCompatActivity(), BaseFragmentCommunicator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(FrameLayout(this))
        LocalDataStore.initiate(applicationContext)
        supportFragmentManager.beginTransaction()
            .add(android.R.id.content, MainFragment())
            .commit()
    }

    override fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, fragment)
            .addToBackStack(fragment.javaClass.name)
            .commit()
    }
}