package com.example.allmovies.repository

import com.example.allmovies.network.ErrorResponse
import com.example.allmovies.network.NetworkResponse
import com.example.allmovies.network.model.dto.MovieDTO
import kotlinx.coroutines.CoroutineDispatcher

interface HomeDataSource {
    suspend fun getListsOfMovies(dispatcher: CoroutineDispatcher, homeResultCallback: (result: NetworkResponse<List<List<MovieDTO>>, ErrorResponse>) -> Unit)
}