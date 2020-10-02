package com.example.allmovies.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allmovies.AppConstants
import com.example.allmovies.di.IoDispatcher
import com.example.allmovies.network.NetworkResponse
import com.example.allmovies.network.TmdbApi
import com.example.allmovies.network.model.dto.MovieDTO
import com.example.allmovies.repository.HomeDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeViewModel @Inject constructor(private val homeDataSource: HomeDataSource, @IoDispatcher private val dispatcher: CoroutineDispatcher): ViewModel() {
    private val _listsOfMovies: MutableLiveData<List<List<MovieDTO>>>? = MutableLiveData()
    val listsOfMovies: LiveData<List<List<MovieDTO>>>? = _listsOfMovies

    private val _errorMessage: MutableLiveData<String>? = MutableLiveData()
    val errorMessage: LiveData<String>? = _errorMessage

    private val _errorMessageVisibility: MutableLiveData<Boolean>? = MutableLiveData()
    val errorMessageVisibility: LiveData<Boolean>? = _errorMessageVisibility

    private val _isLoading: MutableLiveData<Boolean>? = MutableLiveData()
    val isLoading: LiveData<Boolean>? = _isLoading

    fun getListsOfMovies() {
        showErrorMessage(false)
        try {
            viewModelScope.launch(dispatcher) {
                homeDataSource.getListsOfMovies(dispatcher) { result ->
                    when (result) {
                        is NetworkResponse.Success -> {
                            _listsOfMovies?.value = result.body
                            _isLoading?.value = false
                            _errorMessageVisibility?.value = false
                        }
                        is NetworkResponse.NetworkError -> {
                            showErrorMessage(true, AppConstants.NETWORK_ERROR_MESSAGE)
                        }
                        is NetworkResponse.ApiError -> {
                            showErrorMessage(true, AppConstants.API_ERROR_MESSAGE)
                        }
                        is NetworkResponse.UnknownError -> {
                            showErrorMessage(true, AppConstants.UNKNOWN_ERROR_MESSAGE)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }

    private fun showErrorMessage(show: Boolean, message: String? = null) {
        _isLoading?.value = !show
        _errorMessageVisibility?.value = show
        _errorMessage?.value = message
    }
}
