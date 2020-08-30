package com.example.allmovies.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allmovies.network.NetworkResponse
import com.example.allmovies.network.TmdbApi
import kotlinx.coroutines.launch
import javax.inject.Inject

val LOGTAG = "logrequest"

class HomeViewModel @Inject constructor(val tmdbApi: TmdbApi): ViewModel() {
    fun getTrending() {
        viewModelScope.launch {
            val response = tmdbApi.getTrending("pt-BR", 1)
            when (response) {
                is NetworkResponse.Success -> {
                    Log.d(LOGTAG, "Success")
                }
                is NetworkResponse.ApiError -> {
                    Log.d(LOGTAG, "ApiError")
                }
                is NetworkResponse.NetworkError -> {
                    Log.d(LOGTAG, "NetworkError")
                }
                is NetworkResponse.UnknownError -> {
                    Log.d(LOGTAG, "UnknownError")
                }
            }
        }

    }
}
