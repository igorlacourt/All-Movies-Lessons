package com.example.allmovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.allmovies.di.MoviesApplication

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
