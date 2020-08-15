package com.example.allmovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.allmovies.di.MainComponent
import com.example.allmovies.di.MoviesApplication
import com.example.allmovies.ui.HomeViewModel

class MainActivity : AppCompatActivity() {

    lateinit var mainComponent: MainComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        mainComponent = (applicationContext as MoviesApplication).appComponent.mainComponent().create()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}