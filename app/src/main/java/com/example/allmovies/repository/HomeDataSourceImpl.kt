package com.example.allmovies.repository

import com.example.allmovies.AppConstants
import com.example.allmovies.network.ErrorResponse
import com.example.allmovies.network.NetworkResponse
import com.example.allmovies.network.TmdbApi
import com.example.allmovies.network.model.dto.MovieDTO
import com.example.allmovies.network.model.dto.MovieResponseDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class HomeDataSourceImpl @Inject constructor(private val tmdbApi: TmdbApi) : HomeDataSource {
    override suspend fun getListsOfMovies(
        dispatcher: CoroutineDispatcher,
        homeResultCallback: (result: NetworkResponse<List<List<MovieDTO>>, ErrorResponse>) -> Unit
    ) {
        withContext(dispatcher){
            try {
                val trendingMoviesResponse = async { tmdbApi.getTrending(AppConstants.LANGUAGE, 1) }
                val upcomingMoviesResponse = async { tmdbApi.getUpcoming(AppConstants.LANGUAGE, 1) }
                val popularMoviesResponse = async { tmdbApi.getPopular(AppConstants.LANGUAGE, 1) }
                val topRatedMoviesResponse = async { tmdbApi.getTopRated(AppConstants.LANGUAGE, 1) }

                processData(
                    homeResultCallback,
                    trendingMoviesResponse.await(),
                    upcomingMoviesResponse.await(),
                    popularMoviesResponse.await(),
                    topRatedMoviesResponse.await()
                )
            } catch (e: Exception) {
                throw e
            }

        }
    }

    private fun processData(
        homeResultCallback: (result: NetworkResponse<List<List<MovieDTO>>, ErrorResponse>) -> Unit,
        trending: NetworkResponse<MovieResponseDTO, ErrorResponse>,
        upcoming: NetworkResponse<MovieResponseDTO, ErrorResponse>,
        popular: NetworkResponse<MovieResponseDTO, ErrorResponse>,
        topRated: NetworkResponse<MovieResponseDTO, ErrorResponse>) {
        val pair1 = buildResponse(trending)
        val pair2 = buildResponse(upcoming)
        val pair3 = buildResponse(popular)
        val pair4 = buildResponse(topRated)

        when {
            pair1.first == null -> {
                pair1.second?.let { homeResultCallback(it) }
                return
            }
            pair2.first == null -> {
                pair2.second?.let { homeResultCallback(it) }
                return
            }
            pair3.first == null -> {
                pair3.second?.let { homeResultCallback(it) }
                return
            }
            pair4.first == null -> {
                pair4.second?.let { homeResultCallback(it) }
                return
            }
            else -> {
               val resultList = ArrayList<List<MovieDTO>>()
                pair1.first?.let { resultList.add(it) }
                pair2.first?.let { resultList.add(it) }
                pair3.first?.let { resultList.add(it) }
                pair4.first?.let { resultList.add(it) }
                homeResultCallback(NetworkResponse.Success(resultList))
            }
        }
    }

    private fun buildResponse(response: NetworkResponse<MovieResponseDTO, ErrorResponse>)
            : Pair<List<MovieDTO>?, NetworkResponse<List<List<MovieDTO>>, ErrorResponse>?>
    {
        return when(response) {
            is NetworkResponse.Success -> {
                Pair(response.body.results, null)
            }
            is NetworkResponse.ApiError -> {
                Pair(null, NetworkResponse.ApiError(response.body, response.code))
            }
            is NetworkResponse.NetworkError -> {
                Pair(null, NetworkResponse.NetworkError(IOException()))
            }
            is NetworkResponse.UnknownError -> {
                Pair(null, NetworkResponse.UnknownError(Throwable()))
            }
        }
    }

}